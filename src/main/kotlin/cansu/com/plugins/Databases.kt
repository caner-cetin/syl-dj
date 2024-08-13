package cansu.com.plugins

import io.ktor.server.config.ApplicationConfig
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import java.util.Properties

fun DatabaseSupplier(config: ApplicationConfig): Database {
    val url = config.property("db.postgres.url").getString()
    val user = config.property("db.postgres.user").getString()
    val password = config.property("db.postgres.password").getString()
    return Database.connect(
    url = "jdbc:postgresql://$url/db?user=$user&password=$password&reWriteBatchedInserts=true",
    driver = "org.postgresql.Driver",
    )
}

class DatabaseFactory(private val config: ApplicationConfig) {
    fun connect(): Database {
        return DatabaseSupplier(config)
    }
}
