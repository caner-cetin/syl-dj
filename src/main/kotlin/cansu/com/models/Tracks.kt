package cansu.com.models


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
    var artist:String? = null,
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
        "${track.id}\t${track.title}\t${track.album}\t${track.artist}\t${track.musicBrainzAlbumID}\t${track.musicBrainzArtistIDs}\t${track.musicBrainzReleaseTrackID}\t${track.musicBrainzRecordingID}"
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
