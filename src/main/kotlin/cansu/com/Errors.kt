package cansu.com

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import okio.IOException

@Serializable
class ErrorResponse (
    val code: String,
    val message: String,
)
sealed class Errors(val code: HttpStatusCode, val response: ErrorResponse) {
    object DJ0001: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0001",
            "Artist array is required for submitting a track.")
    )
    object DJ0002: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0002", "Album name is required for submitting a track.")
    )
    class DJ0003(e: java.io.IOException): Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0003", "Error while requesting cover art info: ${e.localizedMessage}")
    )
    object DJ0004: Errors (
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0004", "Response is not successful, please check MBID.")
    )
    object DJ0006: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0006", "Metadata in uploaded JSON is not an 'Object'.")
    )
    suspend fun respond(call: ApplicationCall) {
        call.respond(code, response)
    }
    fun codeAndMessage() = Pair(response.code, response.message)
}