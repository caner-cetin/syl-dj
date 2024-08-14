package cansu.com.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class CoverArt(
    val images: List<Image>,
    val release: String,
)
@Serializable
data class Image(
    val approved: Boolean,
    val back: Boolean,
    val comment: String,
    val edit: Long,
    val front: Boolean,
    val id: Long,
    val image: String,
    val thumbnails: Thumbnails,
    val types: List<String>,
)

@Serializable
data class Thumbnails(
    @SerialName("1200")
    val n1200: String,
    @SerialName("250")
    val n250: String,
    @SerialName("500")
    val n500: String,
    val large: String,
    val small: String,
)
