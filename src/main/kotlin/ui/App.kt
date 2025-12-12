package ui

import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import dataClasses.Document

@Composable
fun App(root: RepoNode) {
    var selectedFile by remember { mutableStateOf<FileNode?>(null) }
    var document by remember { mutableStateOf(Document(title = "document", repoName = root.name)) }

    Row(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            Column {
                DocumenterView(
                    document = document
                )

                Spacer(Modifier.height(16.dp))
                Button(onClick = { println("Add heading") }) { Text("Add Heading") }
            }
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            DocuExplorer(
                root = root,
                selected = {selectedFile = it}
            )
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            RepoViewer(selectedFile)
        }
    }
}