package cansu.com.models

import org.jetbrains.exposed.sql.*
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
) {
    companion object {
        fun InsertChunk(chunk: List<ReleaseData>, db: Database) {
            transaction(db) {
                Release.batchInsert(chunk, shouldReturnGeneratedValues = false) {
                    this[Release.id] = it.id
                    this[Release.gid] = it.gid!!
                    this[Release.language] = it.language
                    this[Release.name] = it.name
                }
            }
        }
    }
}