package ui

import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import  dataClasses.FileNode

@Composable
fun DocuViewer(selectedFile: FileNode?) {
    if (selectedFile == null) {
        Text("No file selected")
        return
    }
    Column {
        Text("${selectedFile.name} is selected")
        Text("${selectedFile.content}")
    }
}