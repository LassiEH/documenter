package documentEditHandler

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dataClasses.Document

/**
 * Handler that is responsible for the editing of the user
 * interface
 */
class DocumentEditor(
    initial: Document,
) {
    var document by mutableStateOf(initial)
        private set

    fun addHeading(text: String) {
        document = document.copy(
            parts = (document.parts + DocumentItem.Heading(text)).toMutableList()
        )
    }

    fun addParagraph(text: String) {
        document = document.copy(
            parts = (document.parts + DocumentItem.Paragraph(text)).toMutableList()
        )
    }

    fun addCode(filePath: String, code: String) {
        document = document.copy(
            parts = (document.parts + DocumentItem.Code(filePath, code)).toMutableList()
        )
    }

    fun addImage(title: String, relativePath: String) {
        document = document.copy(
            parts = (document.parts + DocumentItem.Image(title, relativePath)).toMutableList()
        )
    }
}