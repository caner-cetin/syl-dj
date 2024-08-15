package cansu.com

import cansu.com.models.*
import cansu.com.plugins.*
import io.ktor.server.application.*
import io.ktor.server.config.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

@OptIn(DelicateCoroutinesApi::class)
fun main(args: Array<String>) {
    val ftp = GetFTPServer()
    GlobalScope.launch {
        ftp.start()
    }
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
        SchemaUtils.createMissingTablesAndColumns(HighLevelAttributes, Tracks, MirexClusters, Release)
    }
    install(Koin) {
        slf4jLogger()
        modules(configModule, databaseModule, httpClientModule)
    }

    configureRouting()
}