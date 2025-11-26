import dataClasses.Document
import github.RepoHandler
import dataClasses.RepoNode
import dataClasses.FolderNode
import dataClasses.FileNode
import dataClasses.addCode
import dataClasses.addHeading
import dataClasses.addImage
import dataClasses.addParagraph
import github.MockRepoNode
import java.util.Base64
import ui.App
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.runBlocking

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
 /**
@Composable
@Preview
fun App() {
    var counter by remember { mutableStateOf(0) }
    Button(onClick = { counter++ }) {
        Text("Clicked $counter times")
        mainTest()
    }
}
**/
fun mainTest() = runBlocking {
    test()
}


suspend fun test() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val fileAddress = "src/main/kotlin/Main.kt"
    val addList = mutableListOf<String>()
    val repoHandler = RepoHandler()

    val docu = Document(
        title = "tst docu",
        repoName = "tst repo"
    )

    docu.addHeading("Installing")
    docu.addParagraph("How to install")

    val imageBytes = ByteArray(10) { it.toByte() }
    docu.addImage("install", imageBytes)
    docu.addCode("src/main/kotlin/Main.kt", "fun main() {println(hello)}")

    docu.parts.forEach { item ->
        when (item) {
            is DocumentItem.Heading ->
                println(item.text)
            is DocumentItem.Paragraph ->
                println(item.text)
            is DocumentItem.Image ->
                println(item.bytes)
            is DocumentItem.Code ->
                println(item.code)
        }
    }

    // val root = repoHandler.fetchRepo(owner, repo)
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

fun findFile(node: RepoNode, searchedName: String): FileNode? {
    return when (node) {
        is FileNode -> {
            if (node.name == searchedName) node else null
        }
        is FolderNode -> {
            for (child in node.children) {
                val result = findFile(child, searchedName)
                if (result != null) return result
            }
            null

        }
    }
}

// only used with mock data
fun printFileContent(file: FileNode?) {
    val decoded = decodeBase64(file?.content)
    println(decoded)
}


fun decodeBase64(encodedContent: String?): String {
    val cleaned = encodedContent?.filterNot { it.isWhitespace() }
    val decodedBytes = Base64.getDecoder().decode(cleaned)

    return decodedBytes.toString(Charsets.UTF_8)
}