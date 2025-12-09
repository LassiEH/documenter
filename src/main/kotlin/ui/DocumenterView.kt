package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dataClasses.RepoNode

@Composable
fun DocumenterView(root: RepoNode) {
    Box {
        Text("Documenting ${root.name}")
    }

}