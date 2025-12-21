package ui

import DocumentItem
import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import dataClasses.Document
import dataClasses.addCode

@Composable
fun App(root: RepoNode) {
    var selectedFile by remember { mutableStateOf<FileNode?>(null) }
    var document by remember { mutableStateOf(Document(title = "document", repoName = root.name)) }
    var selectedSnippet by remember { mutableStateOf<DocumentItem.Code?>(null) }

    Row(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            Column {
                DocumenterView(
                    document = document
                )

                Spacer(Modifier.height(16.dp))
                Button(onClick = { println("Add heading") }) { Text("Add Heading") }

                Button(onClick = {
                    val snippet = selectedSnippet
                    if (snippet != null && selectedFile != null) {
                        document = document.copy().apply {
                            addCode(selectedFile!!.path, snippet.code)
                        }
                    }
                }) {
                    Text("Add Snippet")
                }

            }
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            DocuExplorer(
                root = root,
                selected = {selectedFile = it}
            )
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            RepoViewer(
                selectedFile,
                onCodeSelected = { snippet ->
                    selectedSnippet = snippet
            }
            )
        }
    }
}