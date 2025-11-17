import github.RepoHandler
import dataClasses.RepoNode
import dataClasses.FolderNode
import dataClasses.FileNode


suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val fileAddress = "src/main/kotlin/Main.kt"
    val addList = mutableListOf<String>()
    val repoHandler = RepoHandler()

    val root = repoHandler.fetchRepo(owner, repo)
    printTree(root)


    repoHandler.closeClient()

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
