package export

import dataClasses.Document
import java.nio.file.Files
import java.nio.file.Path

class MarkdownExporter : DocumentExporter {
    override fun export(document: Document, outputDir: Path) {
        Files.createDirectories(outputDir)
        val imageDir = outputDir.resolve("images")
        Files.createDirectories(imageDir)

        val sb = StringBuilder()

        sb.append("# ${document.title}\n")

        for (item in document.parts) {
            when (item) {
                is DocumentItem.Heading ->
                    sb.append("## ${item.text}\n\n")

                is DocumentItem.Paragraph ->
                    sb.append("${item.text}\n\n")

                is DocumentItem.Image -> {
                    val filename = item.title
                    Files.write(imageDir.resolve(filename), item.bytes)

                    sb.append("![${item.title}](images/$filename)\n\n")
                }

                is DocumentItem.Code -> {
                    sb.append("```")
                    sb.append("${item.code}\n\n")
                    sb.append("```")
                }
            }

        }
        Files.writeString(
            outputDir.resolve("README.md"),
            sb.toString()
        )
    }

}

