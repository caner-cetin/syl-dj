val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val h2_version: String by project
val exposed_version: String by project
plugins {
    application
    kotlin("jvm") version "2.0.10"
    id("io.ktor.plugin") version "2.3.12"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.10"
    id("com.google.cloud.tools.jib") version "3.4.3"
}

jib {
    from {
        image = "amazoncorretto:21"
        platforms {
            platform {
                architecture = "arm64"
                os = "linux"
            }
        }
    }
    to {
        image = "syl-dj-backend"
    }
}

group = "cansu.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    google()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    // https://mvnrepository.com/artifact/org.jetbrains.exposed/exposed-core
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    // https://mvnrepository.com/artifact/org.jetbrains.exposed/exposed-jdbc
    runtimeOnly("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    // https://mvnrepository.com/artifact/io.insert-koin/koin-ktor
    implementation("io.insert-koin:koin-ktor:3.5.6")
    // https://mvnrepository.com/artifact/io.insert-koin/koin-logger-slf4j
    implementation("io.insert-koin:koin-logger-slf4j:3.5.6")
    implementation("org.jetbrains.exposed:exposed-json:$exposed_version")
    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20240303")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-compress
    implementation("org.apache.commons:commons-compress:1.27.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("io.ktor:ktor-server-netty")
    implementation("com.zaxxer:HikariCP:5.1.0")
    //
    // ftp server
    //
    implementation("org.apache.ftpserver:ftpserver-core:1.2.0")
    implementation("org.apache.ftpserver:ftplet-api:1.2.0")
    implementation("org.apache.mina:mina-core:2.2.3")
    implementation("org.slf4j:slf4j-simple:2.1.0-alpha1")
    // https://mvnrepository.com/artifact/org.slf4j/slf4j-api
    implementation("org.slf4j:slf4j-api:2.0.16")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:33.3.0-jre")
    // https://mvnrepository.com/artifact/com.fasterxml.uuid/java-uuid-generator
    implementation("com.fasterxml.uuid:java-uuid-generator:5.1.0")


}
