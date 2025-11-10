package dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val name: String,
    val path: String,
    val type: String,
    val download_url: String? = null,
    val content: String? = null,
)