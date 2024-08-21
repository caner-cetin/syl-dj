package cansu.com.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object ReleaseTags : Table("release_tags_junction") {
    val id: Column<Int> = integer("id")
    val tagId: Column<Int> = reference("tag_id", Tags.id)
}

class ReleaseTagData(
    val id: Int,
    val tagId: Int
)

fun <T : List<ReleaseTagData>> T.insert() {
    ReleaseTags.batchInsert(this) {
        this[ReleaseTags.id] = it.id
        this[ReleaseTags.tagId] = it.tagId
    }
}