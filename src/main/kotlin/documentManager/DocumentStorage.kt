package documentManager

import dataClasses.Document
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.io.File

object DocumentStorage {
    private val json = Json {
        prettyPrint = true
        encodeDefaults = true
        classDiscriminator = "type"
    }

    fun save(document: Document, file: File) {
        val content = json.encodeToString(Document.serializer(), document)
        file.writeText(content)
    }

    fun load(file: File): Document {
        val content = json.decodeFromString(Document.serializer(), file.readText())
        return content
    }
}