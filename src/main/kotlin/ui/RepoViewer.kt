package ui

import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.unit.dp
import  dataClasses.FileNode
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

@Composable
fun RepoViewer(selectedFile: FileNode?) {
    if (selectedFile == null) {
        Text("No file selected")
        return
    }

    val code = selectedFile.content ?: "no code content"

    Column {
        Text("${selectedFile.name} is selected")
        Spacer(Modifier.height(8.dp))
        Box(Modifier.verticalScroll(rememberScrollState())) {
            Text(code, fontFamily = FontFamily.Monospace)
        }
    }
}