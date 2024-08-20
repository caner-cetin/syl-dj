package cansu.com.plugins

import io.ktor.http.content.*
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import org.apache.commons.io.FileUtils
import java.io.BufferedReader
import java.io.File
import kotlin.io.path.outputStream

fun TarArchiveInputStream.readNextJsonFile(reader: BufferedReader): String? {
    val entry = nextEntry ?: return null
    return if (!entry.isDirectory && entry.name.endsWith(".json")) {
        reader.readText()
    } else {
        ""
    }
}

fun File.createLineIteratorSequence(): Sequence<String> {
    return sequence {
        FileUtils.lineIterator(this@createLineIteratorSequence, "UTF-8").use { lineIterator ->
            while (lineIterator.hasNext()) {
                yield(lineIterator.nextLine())
            }
        }
    }
}

fun PartData.FileItem.tempFile(fileName: String?): File {
    val tempFile = kotlin.io.path.createTempFile(fileName ?: "upload", ".tmp")
    this.streamProvider().use { input ->
        tempFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }
    return tempFile.toFile()
}

const val NULL_UUID: String = "00000000-0000-0000-0000-000000000000"
val UUID_REGEX: Regex = Regex("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")

fun Regex.validateAndExtractUUID(input: String): String {
    val match = this.find(input)
    return match?.value ?: NULL_UUID
}