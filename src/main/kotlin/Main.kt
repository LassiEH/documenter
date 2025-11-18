import github.RepoHandler
import dataClasses.RepoNode
import dataClasses.FolderNode
import dataClasses.FileNode
import github.MockRepoNode
import java.util.Base64


suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val fileAddress = "src/main/kotlin/Main.kt"
    val addList = mutableListOf<String>()
    val repoHandler = RepoHandler()

    //val root = repoHandler.fetchRepo(owner, repo)
    val root = MockRepoNode.test()
    printTree(root)

    val file = findFile(root, "Main.kt")
    printFileContent(file)

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

fun findFile(node: RepoNode, searchedRepo: String): FileNode {
    return when (node) {
        is FileNode -> {
            if (node.name == searchedRepo) node else null
        }
        is FolderNode -> {
            for (child in node.children) {
                val result = findFile(child, searchedRepo)
                return result
            }

        }
    } as FileNode
}

fun printFileContent(file: FileNode) {
    val decoded = decodeBase64(file.content)
    println(decoded)
}


fun decodeBase64(encodedContent: String?): String {
    val cleaned = encodedContent?.filterNot { it.isWhitespace() }
    val decodedBytes = Base64.getDecoder().decode(cleaned)

    return decodedBytes.toString(Charsets.UTF_8)
}