package cansu.com.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert

object Tags: Table("tags") {
    val id = integer("id").uniqueIndex()
    val name = text("name")
}

class TagData(
    val id: Int,
    val name: String
)

fun <T: List<TagData>> T.insert() {
    Tags.batchInsert(this) {
        this[Tags.id] = it.id
        this[Tags.name] = it.name
    }
}