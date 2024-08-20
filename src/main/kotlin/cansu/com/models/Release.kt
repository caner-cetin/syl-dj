package cansu.com.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object Release : Table("releases") {
    val id: Column<Int> = integer("id")
    var gid: Column<UUID> = uuid("gid")
    var name: Column<String> = varchar("name", 1024)
    var language: Column<String> = varchar("language", 255)
}

class ReleaseData(
    val id: Int,
    val gid: UUID?,
    val name: String,
    val language: String
)

fun <T : List<ReleaseData>> T.insert(shouldReturnGeneratedValues: Boolean? = false) {
    Release.batchInsert(this, shouldReturnGeneratedValues = shouldReturnGeneratedValues ?: false) {
        this[Release.id] = it.id
        this[Release.gid] = it.gid!!
        this[Release.language] = it.language
        this[Release.name] = it.name
    }
}