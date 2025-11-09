import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

// https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28
// Fetch repository from GitHub
// From documentation: "This endpoint can be used without auth..."
suspend fun fetchRepo(owner: String, repo: String, contents: String) {
    val client = HttpClient(CIO)
    val address = "https://api.github.com/repos/$owner/$repo"
    val response: HttpResponse = client.get(address)
    println(response.status)
    client.close()
}