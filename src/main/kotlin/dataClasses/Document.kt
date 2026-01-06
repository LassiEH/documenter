package dataClasses

import DocumentItem
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.serialization.Serializable
import java.util.UUID

/**
 * Represents a document made by a user.
 *
 * The document is made up of text (headers and paragraphs),
 * images and code snippets.
 */
@Serializable
data class Document(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    val repoName: String,
    val parts: MutableList<DocumentItem> = mutableListOf(),
)