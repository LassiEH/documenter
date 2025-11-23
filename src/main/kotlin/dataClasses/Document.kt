package dataClasses

import DocumentItem
import java.util.UUID

data class Document(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    val repoName: String,
    val parts: MutableList<DocumentItem> = mutableListOf(),
)

fun Document.addHeading(text: String) =
    parts.add(DocumentItem.Heading(text))

fun Document.addParagraph(text: String) =
    parts.add(DocumentItem.Paragraph(text))

fun Document.addImage(title: String, bytes: ByteArray) =
    parts.add(DocumentItem.Image(title, bytes))

fun Document.addCode(filePath: String, code: String) =
    parts.add(DocumentItem.Code(filePath, code))