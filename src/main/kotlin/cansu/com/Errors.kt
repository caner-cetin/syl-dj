package cansu.com

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse (
    val errorCode: String,
    val errorMessage: String,
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
    object DJ0003: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0003",
            "High level attribute data already exists for the track. " +
                         "Please supply `id` field in query parameters for replacing or adding attributes to existing track.")
    )
    object DJ0004: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0004",
            "Track ID is required."
            )
    )
    object DJ0005: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0005",
            "File is not a JSONObject. Maybe array?")
    )
    object DJ0006: Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0006", "Metadata in uploaded JSON is not an 'Object'.")
    )
    suspend fun respond(call: ApplicationCall) {
        call.respond(code, response)
    }

}