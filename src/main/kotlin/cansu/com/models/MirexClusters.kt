package cansu.com.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import java.io.StringReader
import java.sql.Connection
import java.util.*

object MirexClusters : UUIDTable("mirex_mood_clusters") {
    val cluster: Column<List<Float>> = registerColumn("cluster", VectorColumnType(5))
    val trackID: Column<EntityID<UUID>> = reference("track_id", Tracks)
}

class MirexClusterData(
    val id: UUID,
    val cluster: List<Float>,
    val trackID: UUID
)

fun Connection.batchInsertMirexClusters(clusters: List<MirexClusterData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val copyData = clusters.joinToString("\n") { clst ->
        "${clst.id}\t[${clst.cluster.joinToString(",")}]\t${clst.trackID}"
    }
    val copySQL = """
       COPY mirex_mood_clusters (id, cluster, track_id)
       FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySQL,StringReader(copyData))
}