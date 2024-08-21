package cansu.com.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import java.util.*

object Recording : Table("recording") {
    val id: Column<Int> = integer("id").uniqueIndex()
    val mbid: Column<UUID> = uuid("mbid")
}

class RecordingData(
    val id: Int,
    val mbid: String
)

fun <T : List<RecordingData>> T.insert() {
    Recording.batchInsert(this) {
        this[Recording.id] = it.id
        this[Recording.mbid] = UUID.fromString(it.mbid)
    }
}