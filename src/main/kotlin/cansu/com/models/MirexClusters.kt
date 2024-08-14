package cansu.com.models

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID
import java.util.Vector

object MirexClusters : UUIDTable("mirex_mood_clusters") {
    val cluster: Column<List<Float>> = registerColumn("cluster", VectorColumnType(5))
    val trackID: Column<EntityID<UUID>> = reference("track_id", Tracks)
}

class MirexClusterData(
    val id: UUID?,
    val cluster: List<Float>,
    val trackID: UUID
)
