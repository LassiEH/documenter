package documentManager

import dataClasses.Document
import java.io.File
import DocumentItem
import documentEditHandler.DocumentEditor
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DocumentStorageTest {
    @Test
    fun `document is saved and loaded`() {
        val doc = Document(
            title = "My First Doc",
            repoName = "documenter"
        )

        doc.parts.add(DocumentItem.Heading("text"))
        doc.parts.add(DocumentItem.Paragraph("text but paragraph"))
        doc.parts.add(
            DocumentItem.Code(
                filePath = "src/Main.kt",
                code = "fun main() { println(\"Hello\") }"
            )
        )

        val file = File("test-document.json")

        DocumentStorage.save(doc, file)

        val loaded = DocumentStorage.load(file)

        assertEquals(doc.title, loaded.title)
        assertEquals(doc.repoName, loaded.repoName)
        assertEquals(doc.parts.size, loaded.parts.size)
    }

    @Test
    fun `add code snippet`() {
        val editor = DocumentEditor(
            Document(
                title = "My First Doc",
                repoName = "repo"
            )
        )

        editor.addCode(
            filePath = "src/Main.kt",
            code = "fun main() { println(\"Hello\") }"
        )

        assertEquals(1, editor.document.parts.size)
        assertTrue(editor.document.parts.first() is DocumentItem.Code)
    }

    @Test
    fun `code snippet preserves file path and code`() {
        val editor = DocumentEditor(
            Document(
                title = "My First Doc",
                repoName = "repo"
            )
        )

        val filePath = "src/Main.kt"
        val code = "fun main() { println(\"Hello\") }"
        editor.addCode(
            filePath = filePath,
            code = code
        )

        assertEquals(1, editor.document.parts.size)
        val codeItem = editor.document.parts.first() as DocumentItem.Code
        assertEquals(filePath, codeItem.filePath)
        assertEquals(code, codeItem.code)
    }
}

