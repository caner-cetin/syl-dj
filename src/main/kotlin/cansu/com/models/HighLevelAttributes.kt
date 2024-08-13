package cansu.com.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.json.json
import org.postgresql.util.PGobject
import java.util.*


class PGEnum<T: Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}



object HighLevelAttributes: UUIDTable("high_level_attributes") {
    val references_to_track_id: Column<EntityID<UUID>> = reference("track_id", Tracks)
    val attributeName: Column<AttributeNameEnums> =
        customEnumeration(
            "attribute_name",
            "AttributeNames",
            {value -> AttributeNameEnums.valueOf(value as String)},
            {PGEnum("AttributeNames", it)})
    val value: Column<String> = varchar("value", 50)
    val probability: Column<Float> = float("probability")
    val all_values = json<JsonObject>("all_values", Json { prettyPrint = true })
}

data class HighLevelAttributeData(
    val trackID: UUID,
    val attributeName: AttributeNameEnums,
    val value: String,
    val probability: Float,
    val all_values: JsonObject
) {
    companion object {
        fun fromRow(resultRow: ResultRow) = HighLevelAttributeData(
            trackID = resultRow[HighLevelAttributes.references_to_track_id].value,
            attributeName = resultRow[HighLevelAttributes.attributeName],
            value = resultRow[HighLevelAttributes.value],
            probability = resultRow[HighLevelAttributes.probability],
            all_values = resultRow[HighLevelAttributes.all_values],
        )
    }
}