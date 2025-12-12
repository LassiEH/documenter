package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dataClasses.Document
import dataClasses.RepoNode

@Composable
fun DocumenterView(document: Document) {
    Box {
        Text("Documenting ${document.title}")
    }

}