package cansu.com.plugins

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction

fun DatabaseSupplier(config: ApplicationConfig): Database {
    val dbcfg = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://${
            config.property("db.postgres.url").getString()
        }/${config.property("db.postgres.database").getString()}"
        username = config.property("db.postgres.user").getString()
        password = config.property("db.postgres.password").getString()
        isAutoCommit = false
        maximumPoolSize = 20
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }
    dbcfg.addDataSourceProperty("reWriteBatchedInserts", true)
    val ds = HikariDataSource(dbcfg)
    return Database.connect(datasource = ds)
}

class DatabaseFactory(private val config: ApplicationConfig) {
    fun connect(): Database {
        return DatabaseSupplier(config)
    }
}


fun Transaction.CreateIndices() {
    exec(
        """
            SELECT 1;
            """.trimIndent()
    )
}
