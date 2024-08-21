package cansu.com.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object RecordingTags : Table("recording_tags") {
    val id: Column<Int> = reference("id", Recording.id)
    val tagId: Column<Int> = reference("tagId", Tags.id)
}

class RecordingTagData(
    val id: Int,
    val tagId: Int
)

fun <T : List<RecordingTagData>> T.insert() {
    RecordingTags.batchInsert(this) {
        this[RecordingTags.id] = it.id
        this[RecordingTags.tagId] = it.tagId
    }
}