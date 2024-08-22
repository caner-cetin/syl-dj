package cansu.com.endpoints

import cansu.com.Errors
import cansu.com.models.*
import cansu.com.plugins.*
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Semaphore
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.*
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.InputStream
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
        val deleteAfter = call.request.queryParameters["deleteAfter"].toBoolean()
        val callStream = call.receiveStream()
        val tarStream: InputStream = if (fileLocation != null) {
            File(fileLocation).inputStream()
        } else {
            callStream
        }
        val contentProcessor = Dispatchers.IO + SupervisorJob() + CoroutineName("JSONContentProcessor")
        val gson = Gson()
        val sem = Semaphore(128 * 128)
        val jsonElements = channelFlow {
            tarStream.buffered().use { bufferedInputStream ->
                TarArchiveInputStream(bufferedInputStream).use { tarInput ->
                    val reader = tarInput.bufferedReader()
                    while (true) {
                        sem.acquire()
                        val content = tarInput.readNextJsonFile(reader) ?: return@channelFlow
                        if (content != "") {
                            launch(contentProcessor) {
                                try {
                                    val json: HighLevelInfoJSON =
                                        gson.fromJson(content, HighLevelInfoJSON::class.java)
                                    val trackUUID = UUID.randomUUID()
                                    if (json.metadata?.tags?.title != null) {
                                        val tags = json.metadata.tags
                                        val processedTrackData = TrackData(
                                            id = trackUUID,
                                            title = tags.title!!.first(),
                                            artist = tags.artist?.first() ?: "",
                                            album = if (tags.album.isNullOrEmpty()) {
                                                ""
                                            } else {
                                                tags.album.first()
                                            },
                                            musicBrainzAlbumID = try {
                                                UUID_REGEX.validateAndExtractUUID(tags.musicbrainzAlbumid!!.first())
                                            } catch (e: Exception) {
                                                when (e) {
                                                    is NullPointerException, is NoSuchElementException -> tags.musicbrainzAlbumid?.first()
                                                        ?: NULL_UUID

                                                    else -> throw e
                                                }
                                            },
                                            // org.postgresql.util.PSQLException: ERROR: invalid input syntax for type uuid: "056e4f3e-d505-4dad-8ec1-d04f521cbb56/c5ce487b-0462-444e-a184-4de0fef7e028"
                                            //  2024-08-20T15:18:13.663397959Z   Where: COPY tracks, line 483, column musicbrainz_artist_ids: "{056e4f3e-d505-4dad-8ec1-d04f521cbb56/c5ce487b-0462-444e-a184-4de0fef7e028}"
                                            musicBrainzArtistIDs = tags.musicbrainzArtistid?.let {
                                                UUID_REGEX.validateAndExtractUUID(it.first())
                                            } ?: NULL_UUID,
                                            musicBrainzRecordingID = tags.musicbrainzRecordingid?.first()
                                                ?: NULL_UUID,
                                            musicBrainzReleaseTrackID = tags.musicbrainzReleasetrackid?.first()
                                                ?: NULL_UUID,
                                        ).toTabDelimitedString()
                                        val processedMirexData = json.highlevel.moods_mirex?.let { mrx ->
                                            MirexClusterData(
                                                id = UUID.randomUUID(),
                                                cluster = listOf(
                                                    mrx.all.cluster1.toFloat(),
                                                    mrx.all.cluster2.toFloat(),
                                                    mrx.all.cluster3.toFloat(),
                                                    mrx.all.cluster4.toFloat(),
                                                    mrx.all.cluster5.toFloat()
                                                ),
                                                trackID = trackUUID
                                            ).toTabDelimitedString()
                                        }
                                        val highlevelInfoJSONString = gson.toJson(json.highlevel)
                                        val hash: HashMap<*, *>? =
                                            gson.fromJson(highlevelInfoJSONString, HashMap::class.java)
                                        val attrDataList = hash?.mapNotNull { (attrName, attrValue) ->
                                            if (attrName == "moods_mirex") {
                                                null
                                            } else {
                                                val attrJson = attrValue as LinkedTreeMap<*, *>
                                                val value = attrJson["value"]
                                                val probability = attrJson["probability"]
                                                val all = attrJson["all"]

                                                HighLevelAttributeData(
                                                    id = UUID.randomUUID(),
                                                    trackID = trackUUID,
                                                    attributeName = AttributeNameEnums.valueOf(attrName.toString()),
                                                    value = AttributeValue(value.toString()),
                                                    probability = Probability(probability.toString().toFloat()),
                                                    all_values = JSONObject(gson.toJson(all))
                                                ).toTabDelimitedString()
                                            }
                                        }
                                        // emit insert query strings
                                        if (attrDataList != null) {
                                            send(
                                                ProcessedHighLevelData(
                                                    processedTrackData,
                                                    attrDataList,
                                                    processedMirexData
                                                )
                                            )
                                        }
                                    }
                                } finally {
                                    sem.release()
                                }
                            }
                        }
                    }
                }
            }
        }.buffer(DEFAULT_BUFFER_SIZE)
        val batchSize = 5000
        val batch = mutableListOf<ProcessedHighLevelData>()
        jsonElements
            .collect { data ->
                batch.add(data)
                if (batch.size >= batchSize) {
                    batch.flushToDB(db)
                    batch.clear()
                }
            }
        if (batch.isNotEmpty()) {
            batch.flushToDB(db)
        }

        if (deleteAfter) {
            if (fileLocation != null) {
                File(fileLocation).delete()
            }
        }
        call.respond(HttpStatusCode.OK, TrackInfoUploadHighLevelResponse(errorList))
    }
    post("/upload/releases") {
        val multipart = call.receiveMultipart()
        var linesProcessed = 0
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tempFile = part.tempFile("releases.upload")
                val lineSequence = tempFile.createLineIteratorSequence()
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
                    .forEach { chunk -> transaction(db) { chunk.insert() }; linesProcessed += chunk.size }
                tempFile.delete()
            }
        }
        call.respond(HttpStatusCode.Created, "Processed $linesProcessed records.")
    }
    post("/upload/genres") {
        val multipart = call.receiveMultipart()
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tempFile = part.tempFile("genres.upload")
                val lineSequence = tempFile.createLineIteratorSequence()
                val chunkSize = 1000
                lineSequence.map { line ->
                    val spl = line.split("\t")
                    GenreData(
                        mbid = spl[1],
                        name = spl[2],
                    )
                }.chunked(chunkSize)
                    .forEach { chunk -> transaction(db) { chunk.insert() } }
                tempFile.delete()
            }
        }
        call.respond(HttpStatusCode.Created)
    }
    post("/upload/tags") {
        val multipart = call.receiveMultipart()
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tempFile = part.tempFile("tags.upload")
                val chunkSize = 1000
                val lineSequence = tempFile.createLineIteratorSequence()
                lineSequence.map { line ->
                    val spl = line.split("\t")
                    TagData(
                        id = spl[0].toInt(),
                        name = spl[1]
                    )
                }.chunked(chunkSize).forEach { chunk ->
                    transaction(db) { chunk.insert() }
                }
                tempFile.delete()
            }
        }
        call.respond(HttpStatusCode.Created)
    }
    /*
    curl -v --request POST -F upload=@/Users/canercetin/Downloads/mbdump-derived/mbdump/release_tag "http://0.0.0.0:8080/upload/releases/tags"
    release_tag is from **mbdump_derived**, refer to https://data.metabrainz.org/pub/musicbrainz/data/fullexport

    only genre data will be used in tags, but every tag is uploaded just in case.
     */
    post("/upload/releases/tags") {
        val multipart = call.receiveMultipart()
        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tmp = part.tempFile("releases.tags.upload")
                val chunkSize = 1000
                val lineSequence = tmp.createLineIteratorSequence()
                lineSequence.map { line ->
                    val spl = line.split("\t")
                    ReleaseTagData(
                        id = spl[0].toInt(),
                        tagId = spl[1].toInt()
                    )
                }.chunked(chunkSize).forEach { chunk ->
                    transaction(db) { chunk.insert() }
                }
            }
        }
    }
    /*
    Follows same structure with /upload/highlevel. Upload the file from FTP, and give the file location.
     */
    post("/upload/recording") {
        val fileLocation = call.request.queryParameters["fileLocation"]
        val deleteAfter = call.request.queryParameters["deleteAfter"].toBoolean()
        val txt: File? = fileLocation?.let { loc -> File(loc) }
        if (txt == null) {
            call.respond(HttpStatusCode.BadRequest, "No file found at given location")
            return@post
        }
        val chunkSize = 50000
        txt.createLineIteratorSequence().map { line ->
            val spl = line.split("\t")
            RecordingData(
                id = spl[0].toInt(),
                mbid = spl[1]
            )
        }.chunked(chunkSize).forEach { chunk -> launch { transaction(db) { chunk.insert() } } }
        if (deleteAfter) {
            txt.delete()
        }
    }
    post("/upload/recording/tags") {
        call.receiveMultipart().forEachPart { part ->
            if (part is PartData.FileItem) {
                val tmp = part.tempFile("recording.tags")
                val chunkSize = 50000
                tmp.createLineIteratorSequence().map { line ->
                    val spl = line.split("\t")
                    RecordingTagData(
                        id = spl[0].toInt(),
                        tagId = spl[1].toInt()
                    )
                }.chunked(chunkSize).forEach { chunk -> launch { transaction(db) { chunk.insert() } } }
                tmp.delete()
            }
        }
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