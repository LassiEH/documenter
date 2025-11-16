package github

import dataClasses.RepositoryStructure
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.Base64

// https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28
// Fetch repository from GitHub

class RepoHandler {

    private val client = HttpClient(CIO) {
        // https://ktor.io/docs/client-serialization.html#configure_serializer
        // configure serialization to allow JSON data deserialization
        install(ContentNegotiation) {
            json(Json{ ignoreUnknownKeys = true })
        }
    }

    suspend fun fetchRepoContents(owner: String, repo: String, contents: String = ""): List<RepositoryStructure> {
        // address of wanted repository
        val address = "https://api.github.com/repos/$owner/$repo/contents/$contents"
        // get list of repository structures (files, directories) in repo
        val files: List<RepositoryStructure> = client.get(address).body()
        return files
    }

    suspend fun fetchFileContents(owner: String, repo: String, contents: String): String {
        // address of wanted repository
        //return file content as text
        val file: RepositoryStructure = client.get(contents).body()
        // remove whitespace for decoding to work
        val cleaned = file.content?.filterNot { it.isWhitespace() }
        // decode the content
        val decodedBytes = Base64.getDecoder().decode(cleaned)
        // turn to string
        val text = decodedBytes.toString(Charsets.UTF_8)
        return text
    }

    fun closeClient() {
        client.close()
    }

    suspend fun listFiles(repoStructList: List<RepositoryStructure>): List<RepositoryStructure> {
        repoStructList.forEach {
            println(it.name)
        }
        return repoStructList
    }

}