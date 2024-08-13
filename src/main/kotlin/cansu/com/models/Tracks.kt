package cansu.com.models


import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.UUID


object Tracks: UUIDTable("tracks") {
    val artists: Column<List<String>> = array("artist", 255)
    val album: Column<String> = varchar("album", 255)
    val title: Column<String> = varchar("title", 1024)
    val musicbrainzAlbumID: Column<String?> = varchar("musicbrainz_albumid", 255).nullable()
    val musicbrainzArtistIDs: Column<List<String>?> = array<String>("musicbrainz_artistid", 255).nullable()
}

class TrackData (
    val id: UUID,
    val artists: List<String>,
    val album: String,
    val title: String,
    val musicBrainzAlbumID: String?,
    val musicBrainzArtistIDs: List<String>?
) {
}