package cansu.com.models

import org.jetbrains.exposed.sql.ColumnType
import org.postgresql.util.PGobject

class VectorColumnType(private val dimension: Int) : ColumnType<List<Float>>() {
    override fun sqlType(): String = "vector($dimension)"

    override fun valueFromDB(value: Any): List<Float>? {
        return if (value is PGobject) {
            // Convert PGobject to List<Float>
            value.value?.trim('{', '}')?.split(',')?.map { it.toFloat() } ?: emptyList()
        } else {
            null
        }
    }

    override fun valueToDB(value: List<Float>?): Any {
        val vector = (value as List<Float>).joinToString(",")
        return PGobject().apply {
            type = "vector"
            this.value = "[$vector]"
        }
    }
}
