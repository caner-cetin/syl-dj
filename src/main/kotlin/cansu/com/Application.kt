package cansu.com

import cansu.com.models.*
import cansu.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() = runBlocking {
    configureSerialization()

    val config = ApplicationConfig(null)
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
        SchemaUtils.createMissingTablesAndColumns(HighLevelAttributes, Tracks, MirexClusters, Release, Genre, Tags)
    }
    install(Koin) {
        slf4jLogger()
        modules(configModule, databaseModule, httpClientModule)
    }
    val isFTPEnabled = config.property("ftp.enabled").getString().toBoolean()
    if (isFTPEnabled) {
        CoroutineScope(Dispatchers.IO).launch {
            getFTPServer(ApplicationConfig(null)).start()
        }
    }

    configureRouting()
}