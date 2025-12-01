package ui

import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*

@Composable
fun App(root: RepoNode) {
    var selectedFile by remember { mutableStateOf<FileNode?>(null) }

    Row(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Text("Placeholder - place repo tree here?")
            DocuExplorer(
                root = root,
                selected = {selectedFile = it}
            )
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Text("Placeholder - place editor here?")
            DocuViewer(selectedFile)
        }
    }
}