package ui

import androidx.compose.runtime.*
import dataClasses.FileNode
import dataClasses.RepoNode
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.foundation.layout.*

@Composable
fun App() {

    Row(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Text("Placeholder - place repo tree here?")
        }
        Box(Modifier.weight(1f).fillMaxHeight()) {
            Text("Placeholder - place editor here?")
        }
    }
}