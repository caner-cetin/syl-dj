package cansu.com.endpoints

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException

@Serializable
data class HealthcheckResponse(
    val status: String
)

fun Route.healthRoute(db: Database) {
    get("/health") {
        try {
            transaction(db) {
                exec("SELECT 1")
            }
            call.respond(HealthcheckResponse("OK"))
        } catch (e: PSQLException) {
            call.respond(
                HttpStatusCode.InternalServerError,
                "Database is dying, and so do we: ${e.localizedMessage}"
            )
        }
    }
}