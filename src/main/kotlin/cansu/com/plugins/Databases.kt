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
                create index if not exists idx_tracks_album ON tracks (musicbrainz_album_id);
                create index if not exists idx_releases_gid ON releases (gid);
                create index if not exists idx_rtj_id ON release_tags_junction (id, tag_id);
                create index if not exists idx_tags_id ON tags (id, name);
                create index if not exists idx_tg_name ON track_genres (name);
            """.trimIndent()
    )
}
