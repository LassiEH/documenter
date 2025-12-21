package ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import  dataClasses.FileNode
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily

@Composable
fun RepoViewer(
    selectedFile: FileNode?,
    onCodeSelected: (DocumentItem.Code) -> Unit)
{
    if (selectedFile == null) {
        Text("No file selected")
        return
    }

    val lines = remember(selectedFile) {
        selectedFile.content
            ?.lines()
            ?: emptyList()
    }

    var selectionStart by remember { mutableStateOf<Int?>(null) }
    var selectionEnd by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(selectedFile) {
        selectionStart = null
        selectionEnd = null
    }

    Column (Modifier.fillMaxSize().padding(8.dp)) {
        Text(
            text = selectedFile.name,
            style = MaterialTheme.typography.h6
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                lines.forEachIndexed { index, line ->
                    val isSelected =
                        selectionStart != null &&
                                selectionEnd != null &&
                                index in selectionStart!!..selectionEnd!!

                    Text (
                        text = "${index + 1}: $line",
                        fontFamily = FontFamily.Monospace,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                when {
                                    selectionStart == null -> selectionStart = index
                                    selectionEnd == null -> {
                                        selectionEnd = index
                                        if (selectionEnd!! < selectionStart!!) {
                                            val tmp = selectionStart
                                            selectionStart = selectionEnd
                                            selectionEnd = tmp
                                        }
                                    }
                                    else -> {
                                        selectionStart = index
                                        selectionEnd = null
                                    }
                                }
                            }
                            .padding(4.dp),
                    )
            }
        }
    }

        if (selectionStart != null && selectionEnd != null) {
            Spacer(Modifier.height(8.dp))

            Button(onClick = {
                val snippet = lines
                    .subList(selectionStart!!, selectionEnd!! + 1)
                    .joinToString("\n")

                onCodeSelected(
                    DocumentItem.Code(
                        filePath = selectedFile.path,
                        code = snippet
                    )
                )
            }) {
                Text("Add snippet")
            }
        }
    }
}