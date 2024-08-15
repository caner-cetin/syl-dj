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
    val musicbrainzAlbumID: Column<String?> = varchar("musicbrainz_album_id", 108).nullable()
    val musicbrainzArtistIDs: Column<List<String>?> = array<String>("musicbrainz_artist_ids", 108).nullable()
}

class TrackData(
    val id: UUID,
    val artists: List<String>,
    val album: String,
    val title: String,
    val musicBrainzAlbumID: String?,
    val musicBrainzArtistIDs: List<String>?
)

fun Connection.batchInsertTracks(tracks: List<TrackData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val copyData = tracks.joinToString("\n") { track ->
        val artists = track.artists.map { it.replace('"'.toString(), "'") }
        "${track.id}\t${track.title}\t${track.album}\t{${artists.joinToString(",")}}\t${track.musicBrainzAlbumID}\t{${track.musicBrainzArtistIDs?.joinToString(",")}}"
    }

    val copySQL = """
        COPY tracks (id, ${Tracks.title.name}, ${Tracks.album.name}, ${Tracks.artists.name}, ${Tracks.musicbrainzAlbumID.name}, ${Tracks.musicbrainzArtistIDs.name})
        FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySQL, StringReader(copyData))
}