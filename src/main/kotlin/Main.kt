import github.RepoHandler
import dataClasses.RepoNode
import dataClasses.FolderNode
import dataClasses.FileNode
import github.MockRepoNode
import ui.App
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    // To use (my) real repository
    // TEMPORARY SOLUTION WITH RUN BLOCKING
    /*
    val repoHandler = RepoHandler()
    val root = runBlocking {
        fetchRealRoot()
    }
    */

    val root = MockRepoNode.test()
    Window(onCloseRequest = ::exitApplication) {
        App(root)
    }
}

suspend fun fetchRealRoot(): FolderNode {
    val owner = "LassiEH"
    val repo = "documenter"
    val repoHandler = RepoHandler()
    val root = repoHandler.fetchRepo(owner = owner, repo = repo)
    return root
}

fun printTree(node: RepoNode, indent: String = "") {
    when (node) {
        is FolderNode -> {
            println("${node.name}/")
            node.children.forEach { child ->
                printTree(child, "$indent   ")
            }
        }
        is FileNode -> {
            println("$indent ${node.name}")
        }
    }
}
