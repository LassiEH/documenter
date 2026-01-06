package ui

import documentEditHandler.DocumentEditor
import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField
import dataClasses.Document
import documentManager.DocumentStorage
import export.MarkdownExporter
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

fun pickImageFile(): File? {
    val dialog = FileDialog(Frame(), "Select image", FileDialog.LOAD)
    dialog.isVisible = true

    return if (dialog.file != null) {
        File(dialog.directory, dialog.file)
    } else {
        null
    }
}

@Composable
fun App(root: RepoNode) {
    var selectedFile by remember { mutableStateOf<FileNode?>(null) }
    var document by remember { mutableStateOf(Document(title = "document", repoName = root.name)) }
    var inputText by remember { mutableStateOf("") }

    val editor = remember {
        DocumentEditor(
            Document(
                title = document.title,
                repoName = root.name,
            )
        )
    }

    Row(Modifier.fillMaxSize()) {
        // view for the document item being created
        Box(Modifier.weight(1f)) {
            Column {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Text") }
                )
                Button(onClick = {
                    val exporter = MarkdownExporter()
                    exporter.export(document, Paths.get("exports/my-doc"))
                }) {
                    Text("Export")
                }

                Row {
                    Button(onClick = {
                        editor.addHeading(inputText)
                        inputText = ""
                    }) {
                        Text("Add text as heading")
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        editor.addParagraph(inputText)
                        inputText = ""
                    }) {
                        Text("Add text as paragraph")
                    }

                    Button(onClick = {
                        val file = pickImageFile()
                        if (file != null) {
                            try {
                                val fileBytes = file.readBytes()
                                val fileName = file.name

                                val imagePath = Paths.get("images", fileName)
                                Files.createDirectories(imagePath.parent)
                                Files.write(imagePath, fileBytes)

                                editor.addImage(
                                    title = file.name,
                                    relativePath = "images/${file.name}",
                                )

                            } catch (e: Exception) {
                                println("Error reading file: ${e.message}")
                            }
                        }
                    }) {
                        Text("Pick image file")
                    }
                }

                Button(onClick = {
                    DocumentStorage.save(document, File("document.json"))
                }) {
                    Text("Save")
                }

                Button(onClick = {
                    document = DocumentStorage.load(File("document.json"))
                }) {
                    Text("Load")
                }


                DocumenterView(
                    document = editor.document
                )

            }
        }
        // view for the repository tree
        Box(Modifier.weight(1f).fillMaxHeight()) {
            DocuExplorer(
                root = root,
                selected = {selectedFile = it}
            )
        }
        // view for the opened files content
        Box(Modifier.weight(1f).fillMaxHeight()) {
            RepoViewer(
                selectedFile,
                onCodeSelected = { snippet ->
                    editor.addCode(
                        filePath = snippet.filePath,
                        code = snippet.code
                    )
            }
            )
        }
    }
}