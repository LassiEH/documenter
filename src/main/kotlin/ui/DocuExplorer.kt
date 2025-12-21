package ui

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import  dataClasses.*

/**
 * Display repository folders and files in a collapsable tree
 */


@Composable
fun DocuExplorer(
    root: RepoNode,
    selected: (FileNode) -> Unit
) {
    Column {
        Text("Repository", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(8.dp))
        RepoTree(node = root, selected = selected)
    }
}


/**
 * Decides how repository objects should be rendered.
 */
@Composable
fun RepoTree(
    node: RepoNode,
    indent: Int = 0,
    selected: (FileNode) -> Unit
) {
    when (node) {
        is FolderNode -> FolderItem(node, indent, selected)
        is FileNode -> FileItem(node, indent, selected)
    }
}

/**
 * Renders a folder that has expanded and collapsed states.
 * Recursively renders children into a new RepoTree.
 */
@Composable
fun FolderItem(
    folder: FolderNode,
    indent: Int,
    selected: (FileNode) -> Unit
) {
    // value persists across recompositions with remember
    var expanded by remember { mutableStateOf(false) }
    Column{
        Row(
            Modifier
                .fillMaxWidth()
                .padding(start = (indent * 16).dp)
                .clickable { expanded = !expanded}
        ) {
            Text(
                // closed and opened folder have different icons TODO: find some better icons
                text = if (expanded) "- ${folder.name}" else "* ${folder.name}",
                fontWeight = FontWeight.Bold,
            )
        }
        if (expanded) {
            folder.children.forEach { child ->
                // recursively call the RepoTree for all the children
                RepoTree(
                    node = child,
                    indent = indent + 1,
                    selected = selected
                )
            }
        }
    }
}

/**
 * Renders a file and displays name.
 * Selected file is used by RepoViewer to display file content.
 */
@Composable
fun FileItem(
    file: FileNode,
    indent: Int,
    selected: (FileNode) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = (indent * 16).dp)
            .clickable { selected(file) }
    ) {
        Text("> ${file.name}")
    }

}
