import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    //sec()
    fetchRepo(owner, repo, contents)
}

suspend fun sec() {
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("https://httpbin.org/get")
    println(response.status)
    client.close()
}