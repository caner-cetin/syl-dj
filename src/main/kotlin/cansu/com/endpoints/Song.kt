package cansu.com.endpoints

import cansu.com.ErrorResponse
import cansu.com.Errors
import cansu.com.models.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.json.JSONObject
import java.util.UUID
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.json.JSONException
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.Collections

fun Route.trackInfoRoute(db: Database) {
    @Serializable
    class TrackInfoUploadHighLevelResponse (
        val errors: Map<String, ErrorResponse>
    )
    post("/track/info/upload/highlevel") {
        val multipart = call.receiveMultipart()
        val errorList: MutableMap<String, ErrorResponse> = Collections.synchronizedMap(mutableMapOf())

        val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        val trackDataList = Collections.synchronizedList(mutableListOf<TrackData>())
        val highLevelAttributeDataList = Collections.synchronizedList(mutableListOf<HighLevelAttributeData>())

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
                                            errorList[entry.name] = Errors.DJ0001.response
                                            return@launch
                                        }

                                        val trackAlbum = tags.optJSONArray("album")?.optString(0) ?: ""
                                        if (trackAlbum.isEmpty()) {
                                            errorList[entry.name] = Errors.DJ0002.response
                                            return@launch
                                        }

                                        val musicBrainzArtistIDs = tags.optJSONArray("musicbrainz_artistid")?.toList()?.filterIsInstance<String>()
                                        val musicBrainzAlbumID = tags.optJSONArray("musicbrainz_albumid")?.optString(0)

                                        trackDataList.add(TrackData(
                                            id = trackUUID,
                                            title = trackTitle,
                                            artists = trackArtists,
                                            album = trackAlbum,
                                            musicBrainzAlbumID = musicBrainzAlbumID,
                                            musicBrainzArtistIDs = musicBrainzArtistIDs,
                                        ))

                                        val highlevel = json.getJSONObject("highlevel")
                                        highlevel.keys().forEach { highLevelAttributeKey ->
                                            val attr = highlevel.getJSONObject(highLevelAttributeKey)
                                            AttributeNameEnums.entries.find { it.name == highLevelAttributeKey }?.let { attrName ->
                                                highLevelAttributeDataList.add(HighLevelAttributeData(
                                                    trackID = trackUUID,
                                                    attributeName = attrName,
                                                    value = attr.getString("value"),
                                                    all_values = Json.parseToJsonElement(attr.getJSONObject("all").toString()).jsonObject,
                                                    probability = attr.getFloat("probability")
                                                ))
                                            }
                                        }
                                    } catch (e: JSONException) {
                                        errorList[entry.name] = Errors.DJ0006.response
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
        }
        call.respond(HttpStatusCode.OK, TrackInfoUploadHighLevelResponse(errorList))
    }
}