import dataClasses.Repository
import github.fetchRepoContents
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val files = fetchRepoContents(owner, repo, contents)

    files.forEach {
        println(it)
    }

}