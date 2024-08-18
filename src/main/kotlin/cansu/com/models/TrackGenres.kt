package cansu.com.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

class TrackGenres: Table("track_genre_junction") {
    val trackMbid: Column<EntityID<UUID>> = reference("track_mbid", Tracks.id)
    val genreMbid: Column<String> = reference("genre_mbid", Genre.mbid)
}