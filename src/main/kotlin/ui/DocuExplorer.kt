package ui

import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import  dataClasses.*

@Composable
fun DocuExplorer(root: RepoNode, selected: (FileNode) -> Unit) {
    when (root) {
        is FolderNode -> {
            Column {
                Text(root.name)
                root.children.forEach { child ->
                    DocuExplorer(child, selected)
                }
            }
        }
        is FileNode -> {
            //Text(root.name)
            TextButton(onClick = { selected(root) }) {
                Text(root.name)
            }
        }
    }
}

/** from main
 * fun printTree(node: RepoNode, indent: String = "") {
 *     when (node) {
 *         is FolderNode -> {
 *             println("${node.name}/")
 *             node.children.forEach { child ->
 *                 printTree(child, "$indent   ")
 *             }
 *         }
 *         is FileNode -> {
 *             println("$indent ${node.name}")
 *         }
 *     }
 * }
 *
 */