package cansu.com.plugins

import cansu.com.endpoints.healthRoute
import cansu.com.endpoints.trackInfoRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val db by inject<Database>()
    routing {
        healthRoute(db)
        trackInfoRoute(db)
    }
}
