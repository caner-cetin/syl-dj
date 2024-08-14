package cansu.com.endpoints

import cansu.com.ErrorResponse
import cansu.com.Errors
import cansu.com.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.Route
import kotlinx.coroutines.*
import kotlinx.coroutines.future.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import okhttp3.*
import okhttp3.Request
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.json.JSONException
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.resume

fun Route.trackInfoRoute(db: Database) {
    @Serializable
    data class TrackError(
        val filename: String,
        val error: Pair<String, String>
    )

    @Serializable
    class TrackInfoUploadHighLevelResponse(
        val errors: MutableList<TrackError>
    )
    post("/track/info/upload/highlevel") {
        val multipart = call.receiveMultipart()
        val errorList: MutableList<TrackError> = Collections.synchronizedList(mutableListOf())

        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val trackDataList = Collections.synchronizedList(mutableListOf<TrackData>())
        val highLevelAttributeDataList = Collections.synchronizedList(mutableListOf<HighLevelAttributeData>())
        val mirexClusterDataList = Collections.synchronizedList(mutableListOf<MirexClusterData>())

        multipart.forEachPart { part ->
            if (part is PartData.FileItem) {
                val tarBytes = part.streamProvider().use { it.readBytes() }
                ByteArrayInputStream(tarBytes).use { fileInputStream ->
                    TarArchiveInputStream(fileInputStream).use { tarInput ->
                        var entry = tarInput.nextEntry
                        while (entry != null) {
                            if (!entry.isDirectory && entry.name.endsWith(".json")) {
                                val content = ByteArrayOutputStream().use { output ->
                                    tarInput.copyTo(output)
                                    output.toByteArray()
                                }
                                val jsonString = content.toString(Charsets.UTF_8)
                                val json = JSONObject(jsonString)
                                val trackUUID = UUID.randomUUID()

                                scope.launch {
                                    try {
                                        val metadata = json.getJSONObject("metadata")
                                        val tags = metadata.getJSONObject("tags")
                                        val trackTitle = tags.getJSONArray("title")[0].toString()
                                        val trackArtists = (try {
                                            tags.getJSONArray("artists")
                                        } catch (e: JSONException) {
                                            tags.getJSONArray("artist")
                                        }).toList().filterIsInstance<String>()

                                        if (trackArtists.isEmpty()) {
                                            errorList.add(TrackError(entry.name, Errors.DJ0001.codeAndMessage()))
                                            return@launch
                                        }

                                        val trackAlbum = tags.optJSONArray("album")?.optString(0) ?: ""
                                        if (trackAlbum.isEmpty()) {
                                            errorList.add(TrackError(entry.name, Errors.DJ0002.codeAndMessage()))
                                            return@launch
                                        }

                                        val musicBrainzArtistIDs = tags.optJSONArray("musicbrainz_artistid")?.toList()
                                            ?.filterIsInstance<String>()
                                        val musicBrainzAlbumID = tags.optJSONArray("musicbrainz_albumid")?.optString(0)

                                        trackDataList.add(
                                            TrackData(
                                                id = trackUUID,
                                                title = trackTitle,
                                                artists = trackArtists,
                                                album = trackAlbum,
                                                musicBrainzAlbumID = musicBrainzAlbumID,
                                                musicBrainzArtistIDs = musicBrainzArtistIDs,
                                            )
                                        )

                                        val highlevel = json.getJSONObject("highlevel")
                                        highlevel.keys().forEach { highLevelAttributeKey ->
                                            val attr = highlevel.getJSONObject(highLevelAttributeKey)
                                            AttributeNameEnums.entries.find { it.name == highLevelAttributeKey }
                                                ?.let { attrName ->
                                                    if (attrName == AttributeNameEnums.moods_mirex) {
                                                        val clusters = attr.getJSONObject("all")
                                                        val clusterNames = listOf(
                                                            "Cluster1",
                                                            "Cluster2",
                                                            "Cluster3",
                                                            "Cluster4",
                                                            "Cluster5"
                                                        )
                                                        val clusterObjects = clusterNames.associateWith {
                                                            clusters.getDouble(it).toFloat()
                                                        }
                                                        mirexClusterDataList.add(
                                                            MirexClusterData(
                                                                trackID = trackUUID,
                                                                cluster = clusterObjects.values.toList(),
                                                                id = null,
                                                            )
                                                        )
                                                    } else {
                                                        highLevelAttributeDataList.add(
                                                            HighLevelAttributeData(
                                                                trackID = trackUUID,
                                                                attributeName = attrName,
                                                                value = attr.getString("value"),
                                                                all_values = Json.parseToJsonElement(
                                                                    attr.getJSONObject(
                                                                        "all"
                                                                    ).toString()
                                                                ).jsonObject,
                                                                probability = attr.getFloat("probability")
                                                            )
                                                        )
                                                    }
                                                }
                                        }
                                    } catch (e: JSONException) {
                                        errorList.add(TrackError(entry.name, Errors.DJ0006.codeAndMessage()))
                                    }
                                }
                            }
                            entry = tarInput.nextEntry
                        }
                    }
                }
                part.dispose()
            }
        }

        scope.coroutineContext.job.children.forEach { it.join() }

        val uniqueTrackIds = mutableSetOf<UUID>()
        val uniqueTracks = transaction(db) {
            val existingTracks = Tracks
                .select(Tracks.title, Tracks.album, Tracks.artists)
                .map { Triple(it[Tracks.title], it[Tracks.album], it[Tracks.artists]) }
                .toSet()

            trackDataList.filter { track ->
                val isUnique = Triple(track.title, track.album, track.artists) !in existingTracks
                if (isUnique) uniqueTrackIds.add(track.id)
                isUnique
            }
        }

        val uniqueTrackAttrs = highLevelAttributeDataList.filter { it.trackID in uniqueTrackIds }
        val uniqueMirexClusters = mirexClusterDataList.filter { it.trackID in uniqueTrackIds }

        transaction(db) {
            Tracks.batchInsert(uniqueTracks, shouldReturnGeneratedValues = false) {
                this[Tracks.id] = it.id
                this[Tracks.title] = it.title
                this[Tracks.album] = it.album
                this[Tracks.artists] = it.artists
                this[Tracks.musicbrainzAlbumID] = it.musicBrainzAlbumID
                this[Tracks.musicbrainzArtistIDs] = it.musicBrainzArtistIDs
            }
            HighLevelAttributes.batchInsert(uniqueTrackAttrs, shouldReturnGeneratedValues = false) {
                this[HighLevelAttributes.references_to_track_id] = it.trackID
                this[HighLevelAttributes.attributeName] = it.attributeName
                this[HighLevelAttributes.value] = it.value
                this[HighLevelAttributes.probability] = it.probability
                this[HighLevelAttributes.all_values] = it.all_values
            }
            MirexClusters.batchInsert(uniqueMirexClusters, shouldReturnGeneratedValues = false) {
                this[MirexClusters.trackID] = it.trackID
                this[MirexClusters.cluster] = it.cluster
            }
        }
        call.respond(HttpStatusCode.OK, TrackInfoUploadHighLevelResponse(errorList))
    }
}

fun Route.albumRoute(httpClient: OkHttpClient) {
    get("/album/cover/{mbid}") {
        val mbid = call.parameters["mbid"]
        if (mbid == null) {
            call.respond(HttpStatusCode.BadRequest, )
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
        val title = call.parameters["mbid"].let {  }
    }
}