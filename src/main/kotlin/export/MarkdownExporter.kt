package export

import dataClasses.Document
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

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
                    val targetImgPath = outputDir
                        .resolve(item.relativePath)

                    Files.createDirectories(targetImgPath.parent)

                    Files.copy(
                        Paths.get(item.relativePath),
                        targetImgPath,
                        StandardCopyOption.REPLACE_EXISTING
                    )

                    sb.append("![](${item.relativePath})\n\n")
                }

                is DocumentItem.Code -> {
                    sb.append("```\n")
                    sb.append("${item.code}\n\n")
                    sb.append("\n```\n\n")
                }
            }

        }
        Files.writeString(
            outputDir.resolve("README.md"),
            sb.toString()
        )
    }

}

