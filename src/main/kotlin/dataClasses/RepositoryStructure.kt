package dataClasses

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryStructure(
    val name: String,
    val path: String,
    val type: String,
    val url: String,
    val content: String? = null,
    val encoding: String? = null
)