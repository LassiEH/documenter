package ui

import DocumentItem
import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.compose.material.TextField
import dataClasses.Document
import dataClasses.addCode
import dataClasses.addHeading
import dataClasses.addParagraph
import documentManager.DocumentStorage
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

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

    Row(Modifier.fillMaxSize()) {
        // view for the document item being created
        Box(Modifier.weight(1f)) {
            Column {
                TextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    label = { Text("Text") }
                )

                Row {
                    Button(onClick = {
                        val newHeading = DocumentItem.Heading(inputText)
                        document = document.copy(parts = (document.parts + newHeading) as MutableList<DocumentItem>)
                        inputText = ""
                    }) {
                        Text("Add text as heading")
                    }

                    Spacer(Modifier.height(8.dp))

                    Button(onClick = {
                        val newParagraph = DocumentItem.Paragraph(inputText)
                        document = document.copy(parts = (document.parts + newParagraph) as MutableList<DocumentItem>)
                        inputText = ""
                    }) {
                        Text("Add text as paragraph")
                    }

                    Button(onClick = {
                        val file = pickImageFile()
                        if (file != null) {
                            try {
                                val fileBytes = file.readBytes()
                                val newImage = DocumentItem.Image(
                                    title = file.name,
                                    bytes = fileBytes,
                                )
                                document = document.copy(
                                    parts = (document.parts + newImage) as MutableList<DocumentItem>
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
                    document = document
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
                    val updatedParts = document.parts + snippet
                    document = document.copy(parts = updatedParts as MutableList<DocumentItem>)
            }
            )
        }
    }
}