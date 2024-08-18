package cansu.com.plugins

import kotlinx.coroutines.yield
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream
import java.io.BufferedInputStream
import java.io.BufferedReader

fun TarArchiveInputStream.readNextJsonFile(reader: BufferedReader): String? {
    val entry = nextEntry ?: return null
    return if (!entry.isDirectory && entry.name.endsWith(".json")) {
        reader.readText()
    } else {
        ""
    }
}