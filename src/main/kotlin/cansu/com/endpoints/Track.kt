package cansu.com.endpoints

import cansu.com.Errors
import cansu.com.models.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import okhttp3.*
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.io.FileUtils
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.json.JSONException
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.sql.Connection
import java.util.*
import kotlin.coroutines.resume

@OptIn(ExperimentalCoroutinesApi::class)
fun Route.uploadRoute(db: Database) {
    @Serializable
    data class TrackError(
        val filename: String,
        val error: Pair<String, String>
    )

    @Serializable
    class TrackInfoUploadHighLevelResponse(
        val errors: MutableList<TrackError>
    )
    /**
     * POST /upload/highlevel
     *
     * Uploads AcousticBrainz high level data dump to database https://data.metabrainz.org/pub/musicbrainz/acousticbrainz/dumps/
     * For sample uploads, or any small to medium files, supply the TAR dump from POST body directly.
     * curl -v --request POST -T "/file/location" http://0.0.0.0:8080/upload/highlevel
     *
     * For large sized uploads (POST body does not support anything above 100 mbs), you have two choices.
     * 1. Upload the dump from your system to the server with FTP
     * 2. Let server download the dump
     * For local testing, first one is the most feasible choice. Yes, I am too lazy to bind dump folder in docker-compose.
     * FTP admin's username, password, home directory can be set in the environment variables at Docker Compose file.
     *
     * For FTP:
     *  - Download the dump as usual.
     *  - Upload the dump:
     *      ```
     *      curl --user "name:pwd" -T "/file/location" ftp://localhost:2221/
     *      ```
     *  - Note the directory you just uploaded. By uploading to ftp://localhost:2221/, you are uploading to the
     *    specified home directory in Docker Compose.
     *  - Fill query parameter "fileLocation" based on the directory uploaded. For example, if the dump is uploaded with
     *      ```
     *      curl --user "sylftp:3131" -T /Users/canercetin/homework/catgirl.tar ftp://localhost:2221/
     *      ```
     *  - fileLocation should be set to `/homedirectory/catgirl.tar`.
     *  In full:
     *      ```
     *      ~ ➤ curl --user "sylftp:b7c790faf3c5a7b230a3fff9cf9cb319" -T /Users/canercetin/Downloads/abrainz/acousticbrainz-highlevel-sample-json-20220623-0.tar ftp://localhost:2221/
     *          % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
     *                                  Dload  Upload   Total   Spent    Left  Speed
     *          100 1080M    0     0  100 1080M      0   107M  0:00:10  0:00:10 --:--:--  113M
     *      ~ ➤ curl -v --request POST "http://0.0.0.0:8080/upload/highlevel?fileLocation=/sylftp/upload/acousticbrainz-highlevel-sample-json-20220623-0.tar"
     *          *   Trying 0.0.0.0:8080...
     *          * Connected to 0.0.0.0 (0.0.0.0) port 8080
     *          > POST /upload/highlevel?fileLocation=/sylftp/upload/acousticbrainz-highlevel-sample-json-20220623-0.tar HTTP/1.1
     *          > Host: 0.0.0.0:8080
     *          > User-Agent: curl/8.6.0
     *          > Accept:
     *          >
     *          < HTTP/1.1 200 OK
     *          < Content-Length: 340
     *          < Content-Type: application/json
     *     ```
     *
     *
     * For downloading the dump from server directly:
     *  First of all, ensure that FTP is disabled on Docker Compose. FTP's only purpose is to feed dumps from host machine for local testing.
     *  wip
     */
    post("/upload/highlevel") {
        val errorList: MutableList<TrackError> = Collections.synchronizedList(mutableListOf())
        val fileLocation = call.request.queryParameters["fileLocation"]
        val callStream = call.receiveStream()
        val tarStream: InputStream = if (fileLocation != null) {
            File(fileLocation).inputStream()
        } else {
            callStream
        }
        val jsonElements: Sequence<Pair<String, Map<String, JsonElement>>> = sequence {
            tarStream.buffered().use { bufferedInputStream ->
                TarArchiveInputStream(bufferedInputStream).use { tarInput ->
                    while (true) {
                        val entry = tarInput.nextEntry ?: break
                        if (!entry.isDirectory && entry.name.endsWith(".json")) {

                            val jsonString = tarInput.bufferedReader().readText()
                            val json: Map<String, JsonElement> =
                                Json.parseToJsonElement(jsonString).jsonObject
                            val filteredJsons = json.filterValues {
                                try {
                                    it.jsonNull
                                    // if we are still here, accessing jsonNull did not throw any exceptions
                                    // so we are safe to say that this element is null
                                    // filtered
                                    false
                                } catch (e: IllegalArgumentException) {
                                    // it is anything other than json null
                                    true
                                }
                            }
                            yield(Pair(entry.name, filteredJsons))
                        }
                    }
                }
            }
        }
        val chunkSize = 1000

        val dataflow: Flow<Triple<TrackData, List<HighLevelAttributeData>, MirexClusterData>> = flow {
            for (chunk in jsonElements.chunked(chunkSize)) {
                for ((fileName, json) in chunk) {
                    val trackUUID = UUID.randomUUID()
                    try {
                        val metadata: JsonObject? = json["metadata"]?.jsonObject
                        if (metadata == null) {
                            errorList.add(TrackError(fileName, Errors.DJ0008.codeAndMessage()))
                            return@flow
                        }
                        val tags = metadata["tags"]?.jsonObject
                        if (tags == null) {
                            errorList.add(TrackError(fileName, Errors.DJ0009.codeAndMessage()))
                            return@flow
                        }
                        val trackTitle = tags.getValue("title").jsonArray[0].toString()
                        val trackArtistArray: JsonArray = (try {
                            tags.getValue("artist").jsonArray
                        } catch (e: NoSuchElementException) {
                            tags.getValue("artists").jsonArray
                        })
                        val trackArtists = trackArtistArray.map { it.jsonPrimitive.toString() }

                        if (trackArtists.isEmpty()) {
                            errorList.add(TrackError(fileName, Errors.DJ0001.codeAndMessage()))
                            return@flow
                        }
                        val trackAlbum = tags["album"]?.jsonArray?.get(0).toString()
                        if (trackAlbum.isEmpty()) {
                            errorList.add(TrackError(fileName, Errors.DJ0002.codeAndMessage()))
                            return@flow
                        }

                        val musicBrainzArtistIDs =
                            tags["musicbrainz_artistid"]?.jsonArray?.map { it.jsonPrimitive.toString() }
                        val musicBrainzAlbumID = tags["musicbrainz_albumid"]?.jsonArray?.get(0).toString()
                        var processedTrackData = TrackData(
                            id = trackUUID,
                            title = trackTitle,
                            artists = trackArtists,
                            album = trackAlbum,
                            musicBrainzAlbumID = musicBrainzAlbumID,
                            musicBrainzArtistIDs = musicBrainzArtistIDs,
                        )
                        var processedHighLevelAttributeData = mutableListOf<HighLevelAttributeData>()
                        var processedMirexClusterData: MirexClusterData? = null
                        val highlevel = json["highlevel"]?.jsonObject
                        if (highlevel == null) {
                            errorList.add(TrackError(fileName, Errors.DJ0010.codeAndMessage()))
                            return@flow
                        }
                        highlevel.keys.forEach { highLevelAttributeKey ->
                            val attr = highlevel[highLevelAttributeKey]!!.jsonObject
                            AttributeNameEnums.entries.find { it.name == highLevelAttributeKey }
                                ?.let { attrName ->
                                    if (attrName == AttributeNameEnums.moods_mirex) {
                                        val clusters = attr["all"]?.jsonObject!!
                                        val clusterNames = listOf(
                                            "Cluster1",
                                            "Cluster2",
                                            "Cluster3",
                                            "Cluster4",
                                            "Cluster5"
                                        )
                                        val clusterObjects = clusterNames.associateWith {
                                            clusters[it]!!.jsonPrimitive.float
                                        }
                                        processedMirexClusterData = MirexClusterData(
                                            trackID = trackUUID,
                                            cluster = clusterObjects.values.toList(),
                                            id = UUID.randomUUID(),
                                        )
                                    } else {
                                        processedHighLevelAttributeData.add(
                                            HighLevelAttributeData(
                                                id = UUID.randomUUID(),
                                                trackID = trackUUID,
                                                attributeName = attrName,
                                                value = attr["value"].toString(),
                                                all_values = Json.parseToJsonElement(
                                                    attr["all"].toString()
                                                ).jsonObject,
                                                probability = attr["probability"]?.jsonPrimitive?.float
                                                    ?: 0.0f
                                            )
                                        )
                                    }
                                }
                        }
                        emit(Triple(processedTrackData, processedHighLevelAttributeData, processedMirexClusterData!!))
                    } catch (e: JSONException) {
                        errorList.add(TrackError(fileName, Errors.DJ0006.codeAndMessage()))
                    }
                }
            }
        }.flowOn(Dispatchers.Default)

        val databaseThresholdSize = 100
        val trackDataList: MutableList<TrackData> = mutableListOf()
        val highLevelAttributeDataList = mutableListOf<HighLevelAttributeData>()
        val mirexClusterDataList = mutableListOf<MirexClusterData>()

        withContext(Dispatchers.Default) {
            dataflow
                .collect { data ->
                    trackDataList.add(data.first)
                    highLevelAttributeDataList.addAll(data.second)
                    mirexClusterDataList.add(data.third)

                    if (trackDataList.size >= databaseThresholdSize) {
                        newSuspendedTransaction(Dispatchers.IO, db, Connection.TRANSACTION_READ_COMMITTED) {
                            val connection = this.connection.connection as Connection
                            connection.batchInsertTracks(trackDataList)
                            trackDataList.clear()
                            connection.batchInsertHighLevelAttributes(highLevelAttributeDataList)
                            highLevelAttributeDataList.clear()
                            connection.batchInsertMirexClusters(mirexClusterDataList)
                            mirexClusterDataList.clear()
                        }
                    }
                }
            // Insert any remaining data
            if (trackDataList.isNotEmpty() || highLevelAttributeDataList.isNotEmpty() || mirexClusterDataList.isNotEmpty()) {
                // todo: dry
                newSuspendedTransaction(Dispatchers.IO, db, Connection.TRANSACTION_READ_COMMITTED) {
                    val connection = this.connection.connection as Connection
                    connection.batchInsertTracks(trackDataList)
                    trackDataList.clear()
                    connection.batchInsertHighLevelAttributes(highLevelAttributeDataList)
                    highLevelAttributeDataList.clear()
                    connection.batchInsertMirexClusters(mirexClusterDataList)
                    mirexClusterDataList.clear()
                }
            }
        }
        call.respond(HttpStatusCode.OK, TrackInfoUploadHighLevelResponse(errorList))
    }
    post("/upload/releases") {
        val multipart = call.receiveMultipart()
        var linesProcessed = 0
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tempFile = kotlin.io.path.createTempFile(prefix = "releases_upload", suffix = ".tmp")
                part.streamProvider().use { input ->
                    tempFile.toFile().outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val lineSequence = sequence {
                    FileUtils.lineIterator(tempFile.toFile(), "UTF-8").use { lineIterator ->
                        while (lineIterator.hasNext()) {
                            yield(lineIterator.nextLine())
                        }
                    }
                }
                val chunkSize = 1000
                lineSequence.map { line ->
                    val spl = line.split("\t")
                    ReleaseData(
                        id = spl[0].toInt(),
                        gid = UUID.fromString(spl[1]),
                        name = spl[2],
                        language = spl[7]
                    )
                }.chunked(chunkSize)
                    .forEach { chunk -> ReleaseData.InsertChunk(chunk, db); linesProcessed += chunk.size }
                tempFile.toFile().delete()
            }
        }
        call.respond(HttpStatusCode.Created, "Processed $linesProcessed records.")
    }
}

fun Route.albumRoute(httpClient: OkHttpClient) {
    get("/album/cover/{mbid}") {
        val mbid = call.parameters["mbid"]
        if (mbid == null) {
            call.respond(HttpStatusCode.BadRequest)
        }
        val apiURI = "https://coverartarchive.org/release/$mbid"
        var errorMessage: Errors? = null
        val coverArt: CoverArt = suspendCancellableCoroutine { continuation ->
            val request = Request.Builder()
                .url(apiURI)
                .header("Content-Type", "application/json")
                .build()
            httpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    errorMessage = Errors.DJ0003(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            errorMessage = Errors.DJ0004
                        } else {
                            val body = response.body!!.string()
                            val coverArt = Json.decodeFromString<CoverArt>(body)
                            continuation.resume(coverArt)
                        }
                    }
                }
            })
        }
        if (errorMessage != null) {
            call.respond(HttpStatusCode.BadRequest, errorMessage!!.codeAndMessage())
            return@get
        }
        call.respond(HttpStatusCode.OK, coverArt)
    }
    get("/album/info/{title}") {
        call.respond(HttpStatusCode.NotImplemented)
    }


}