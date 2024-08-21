package cansu.com.models

import com.alibaba.fastjson2.JSONObject
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.json.json
import org.postgresql.copy.CopyManager
import org.postgresql.core.BaseConnection
import org.postgresql.util.PGobject
import java.io.StringReader
import java.sql.Connection
import java.util.*


class PGEnum<T : Enum<T>>(enumTypeName: String, enumValue: T?) : PGobject() {
    init {
        value = enumValue?.name
        type = enumTypeName
    }
}


object HighLevelAttributes : UUIDTable("high_level_attributes") {
    val trackID: Column<EntityID<UUID>> = reference("track_id", Tracks)
    val attributeName: Column<AttributeNameEnums> =
        customEnumeration(
            "attribute_name",
            "AttributeNames",
            { value -> AttributeNameEnums.valueOf(value as String) },
            { PGEnum("AttributeNames", it) })
    val value: Column<String> = varchar("value", 50)
    val probability: Column<Float> = float("probability")
    val all_values = json<JsonObject>("all_values", Json { prettyPrint = true })
}

data class HighLevelAttributeData(
    var id: UUID,
    val trackID: UUID,
    val attributeName: AttributeNameEnums,
    val value: AttributeValue,
    val probability: Probability,
    val all_values: JSONObject
)

@JvmInline
value class AttributeValue(val value: String)

@JvmInline
value class Probability(val value: Float)

fun Connection.batchInsertHighLevelAttributes(attributes: List<HighLevelAttributeData>) {
    val copyManager = CopyManager(unwrap(BaseConnection::class.java))
    val copyData = attributes.joinToString("\n") { attr ->
        "${attr.id}\t${attr.trackID}\t${attr.attributeName}\t${attr.value.value}\t${attr.probability.value}\t[${
            attr.all_values.map { it.value }.joinToString(",")
        }]"
    }
    val copySql = """
        COPY high_level_attributes (id, track_id, attribute_name, value, probability, all_values)
        FROM STDIN WITH (FORMAT TEXT, DELIMITER E'\t')
    """.trimIndent()
    copyManager.copyIn(copySql, StringReader(copyData))
}