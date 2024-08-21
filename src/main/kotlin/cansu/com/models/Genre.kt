package cansu.com.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object Genre : Table("track_genres") {
    val mbid: Column<String> = varchar("mbid", 512)
    val name: Column<String> = varchar("name", 256)

}

class GenreData(
    val mbid: String,
    val name: String
)

fun <T : List<GenreData>> T.insert() {
    Genre.batchInsert(this) {
        this[Genre.mbid] = it.mbid
        this[Genre.name] = it.name
    }
}