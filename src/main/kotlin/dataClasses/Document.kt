package dataClasses

import DocumentItem
import java.util.UUID

data class Document(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    val repoName: String,
    val parts: MutableList<DocumentItem> = mutableListOf(),
)
