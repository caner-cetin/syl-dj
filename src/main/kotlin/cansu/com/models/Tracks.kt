package cansu.com.models


import cansu.com.plugins.escapeSpecialCharacters
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.StringReader
import java.sql.Connection
import java.util.*


object Tracks : UUIDTable("tracks") {
    val artist: Column<String?> = text("artist").nullable()

    // 2024-08-21T15:24:06.674143050Z   Where: COPY tracks, line 1253, column album: "Most of the Remixes We've Made Over The Years Except For The One For Einstürzende Neubauten Because..."
    // who did this?
    val album: Column<String> = varchar("album", 1024)
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

fun Connection.batchInsertTracks(tracks: List<String>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val copyData = tracks.joinToString("\n")
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


fun TrackData.toTabDelimitedString(): String {
    return StringBuilder().apply {
        append(id)
        append('\t')
        append(title.escapeSpecialCharacters())
        append('\t')
        append(album.escapeSpecialCharacters())
        append('\t')
        append(artist?.escapeSpecialCharacters() ?: "")
        append('\t')
        append("$musicBrainzAlbumID")
        append('\t')
        append(musicBrainzArtistIDs)
        append('\t')
        append(musicBrainzReleaseTrackID)
        append('\t')
        append(musicBrainzRecordingID)
    }.toString()
}

data class ProcessedHighLevelData(val track: String, val attributes: List<String>, val cluster: String?)
suspend fun <T : List<ProcessedHighLevelData>> T.flushToDB(db: Database) {
    newSuspendedTransaction(Dispatchers.IO, db, Connection.TRANSACTION_SERIALIZABLE) {
        val connection = this.connection.connection as Connection
        connection.batchInsertTracks(this@flushToDB.map { it.track })
        connection.batchInsertHighLevelAttributes(this@flushToDB.flatMap { it.attributes })
        connection.batchInsertMirexClusters(this@flushToDB.mapNotNull { it.cluster })
    }
}