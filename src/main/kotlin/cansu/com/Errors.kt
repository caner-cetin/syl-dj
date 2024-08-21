package cansu.com

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

@Serializable
class ErrorResponse(
    val code: String,
    val message: String,
)

sealed class Errors(val code: HttpStatusCode, val response: ErrorResponse) {
    object DJ0001 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse(
            "DJ-0001",
            "Artist array is required for submitting a track."
        )
    )

    object DJ0002 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0002", "Album name is required for submitting a track.")
    )

    class DJ0003(e: java.io.IOException) : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0003", "Error while requesting cover art info: ${e.localizedMessage}")
    )

    object DJ0004 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0004", "Response is not successful, please check MBID.")
    )

    object DJ0005 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0005", "Album title is required for querying album info with /album/info/{title} route.")
    )

    object DJ0006 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0006", "Metadata in uploaded JSON is not an 'Object'.")
    )

    object DJ0007 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0007", "MBID is required for querying album cover art with /album/cover/{mbid}.")
    )

    object DJ0008 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0008", "Metadata information does not exist for track file, skipping.")
    )

    object DJ0009 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0008", "Tag information in metadata does not exist for track file, skipping.")
    )

    object DJ0010 : Errors(
        HttpStatusCode.BadRequest,
        ErrorResponse("DJ-0010", "High level information does not exist for track file, skipping.")
    )

    suspend fun respond(call: ApplicationCall) {
        call.respond(code, response)
    }

    fun codeAndMessage() = Pair(response.code, response.message)
}