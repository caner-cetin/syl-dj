package cansu.com.models


import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.StringReader
import java.sql.Connection
import java.util.*


object Tracks : UUIDTable("tracks") {
    val artists: Column<List<String>> = array("artists", 255)
    val album: Column<String> = varchar("album", 255)
    val title: Column<String> = varchar("title", 1024)
    val musicBrainzAlbumID: Column<UUID?> = uuid("musicbrainz_album_id").nullable()
    val musicBrainzArtistIDs: Column<List<UUID>?> = array<UUID>("musicbrainz_artist_ids", 108).nullable()
    val musicBrainzReleaseTrackID: Column<UUID?> = uuid("musicbrainz_release_track_id").nullable()
    val musicBrainzRecordingID: Column<UUID?> = uuid("musicbrainz_recording_id").nullable()
}

class TrackData(
    val id: UUID,
    var artists: List<String>? = null,
    val album: String,
    val title: String,
    val musicBrainzAlbumID: String?,
    val musicBrainzArtistIDs: List<String>?,
    val musicBrainzRecordingID: String?,
    val musicBrainzReleaseTrackID: String?
)

fun Connection.batchInsertTracks(tracks: List<TrackData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val trackData = tracks.map { track ->
        track.artists = if (track.artists == null) {
            emptyList()
        } else {
            // Replace double quotes with single quotes and escape any other problematic characters
            track.artists!!.map { artist ->
                artist.replace('"', '\'')
                    .replace(",", "\\,")
                    .replace("{", "")
                    .replace("}", "")
            }
        }
        track
    }

    var copyData = trackData.joinToString("\n") { track ->
        val artists = track.artists?.joinToString(",")?.let { "{$it}" } ?: "{}"
        val musicBrainzArtistIDs = track.musicBrainzArtistIDs?.joinToString(",")?.let { "{$it}" } ?: "{}"
        if (artists.startsWith("{") && artists.endsWith("}")) {
            // artists in a good shape
            "${track.id}\t${track.title}\t${track.album}\t$artists\t${track.musicBrainzAlbumID}\t$musicBrainzArtistIDs\t${track.musicBrainzReleaseTrackID}\t${track.musicBrainzRecordingID}"
        } else {
            // i dont know how the fuck but
            // we still manage to get 2024-08-20T12:01:59.230845507Z org.postgresql.util.PSQLException: ERROR: malformed array literal: "785481ee-bc5c-40a6-ae46-db4c5246f990"
            // even after wrapping the artists in {} literally two lines before
            // like we escape characters, join the artists carefully, and then one motherfucking track comes drooling
            // "mom i ate the crayon oops".
            //
            // like there is some errors in tracks
            // 2024-08-20T13:06:56.792794922Z org.postgresql.util.PSQLException: ERROR: malformed array literal: "{(18) Six Études pour la main gauche seule, Op. 135,,3 Moto perpetuo- Allegretto}"
            // which doesnt crass the whole process and life goes on normally, but this "broski you forgot {"
            // error crashes the entire batch upload process.
            // YOU UPLOADED 430.000 TRACKS WHAT DO YOU MEAN THAT I AM SENDING YOU MALFORMED ARRAY LITERAL YOU DUMBASS BITCH
            //
            // this error wont happen if i used mongo
            // but who the fuck uses mongo anyways
            //
            // bitching ends
            // return placeholder to be filtered later
            ""
        }
    }
    copyData = copyData.lines().filter { it != "" }.joinToString("\n")

    val copySQL = """
        COPY tracks (
            ${Tracks.id.name}, 
            ${Tracks.title.name}, 
            ${Tracks.album.name}, 
            ${Tracks.artists.name}, 
            ${Tracks.musicBrainzAlbumID.name}, 
            ${Tracks.musicBrainzArtistIDs.name},
            ${Tracks.musicBrainzReleaseTrackID.name},
            ${Tracks.musicBrainzRecordingID.name}
        )
        FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySQL, StringReader(copyData))
}
