package export

import dataClasses.Document
import java.nio.file.Path

interface DocumentExporter {
    fun export(document: Document, outputDir: Path)
}