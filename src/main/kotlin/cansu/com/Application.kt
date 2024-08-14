package cansu.com

import cansu.com.models.AttributeNameEnums
import cansu.com.models.HighLevelAttributes
import cansu.com.models.MirexClusters
import cansu.com.models.Tracks
import cansu.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.dsl.module

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()

    val configModule = module {
        single { ApplicationConfig(null) }
    }
    val databaseModule = module {
        single { DatabaseFactory(get()) }
        single { get<DatabaseFactory>().connect() }
    }
    val httpClientModule = module {
        single { HttpClientFactory() }
    }
    transaction(DatabaseSupplier(ApplicationConfig(null))) {
        exec("CREATE EXTENSION  IF NOT EXISTS vector")
        exec(AttributeNameEnums.CreatePSQLTypeIfNotExists())
        SchemaUtils.createMissingTablesAndColumns(HighLevelAttributes, Tracks, MirexClusters)
    }
    install(Koin) {
        slf4jLogger()
        modules(configModule, databaseModule, httpClientModule)
    }

    configureRouting()
}