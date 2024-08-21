package cansu.com.models


import cansu.com.plugins.escapeSpecialCharacters
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.StringReader
import java.sql.Connection
import java.util.*


object Tracks : UUIDTable("tracks") {
    val artist: Column<String?> = text("artist").nullable()
    val album: Column<String> = varchar("album", 255)
    val title: Column<String> = varchar("title", 1024)
    val musicBrainzAlbumID: Column<UUID?> = uuid("musicbrainz_album_id").nullable()
    val musicBrainzArtistIDs: Column<UUID?> = uuid("musicbrainz_artist_ids").nullable()
    val musicBrainzReleaseTrackID: Column<UUID?> = uuid("musicbrainz_release_track_id").nullable()
    val musicBrainzRecordingID: Column<UUID?> = uuid("musicbrainz_recording_id").nullable()
}

class TrackData(
    val id: UUID,
    var artist: String? = null,
    val album: String,
    val title: String,
    val musicBrainzAlbumID: String?,
    val musicBrainzArtistIDs: String?,
    val musicBrainzRecordingID: String?,
    val musicBrainzReleaseTrackID: String?
)

fun Connection.batchInsertTracks(tracks: List<TrackData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val copyData = tracks.joinToString("\n") { track ->
        // 2024-08-20T16:03:17.804132169Z   Where: COPY tracks, line 130: "4b07178f-dc1b-45bc-b3e3-f02e06f7566d	Internet Connection	/\/\ /\ Y /\	M.I.A.	785481ee-bc5c-40a6-ae46..."
        // WHO IN THEIR RIGHT FUCKING MIND NAMES AN ALBUM /\/\ /\ Y /\ FOR FUCKING SAKE ARE YOU FUCKING KIDDING ME
        //
        // i just wrote String.escapeSpecialCharacters() because of this
        listOf(
            track.id,
            track.title.escapeSpecialCharacters(),
            track.album.escapeSpecialCharacters(),
            track.artist?.escapeSpecialCharacters(),
            track.musicBrainzAlbumID,
            track.musicBrainzArtistIDs,
            track.musicBrainzReleaseTrackID,
            track.musicBrainzRecordingID
        ).joinToString("\t")
    }

    val copySQL = """
        COPY tracks (
            ${Tracks.id.name}, 
            ${Tracks.title.name}, 
            ${Tracks.album.name}, 
            ${Tracks.artist.name}, 
            ${Tracks.musicBrainzAlbumID.name}, 
            ${Tracks.musicBrainzArtistIDs.name},
            ${Tracks.musicBrainzReleaseTrackID.name},
            ${Tracks.musicBrainzRecordingID.name}
        )
        FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySQL, StringReader(copyData))
}
