package github

import dataClasses.Repository
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28
// Fetch repository from GitHub
suspend fun fetchRepoContents(owner: String, repo: String, contents: String = ""): List<Repository> {
    val client = HttpClient(CIO) {
        // https://ktor.io/docs/client-serialization.html#configure_serializer
        // configure serialization to allow JSON data deserialization
        install(ContentNegotiation) {
            json(Json{ignoreUnknownKeys = true})
        }
    }
    val address = "https://api.github.com/repos/$owner/$repo/contents/$contents"
    val files: List<Repository> = client.get(address).body()
    client.close()
    return files
}