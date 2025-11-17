package github

import dataClasses.RepositoryStructure
import dataClasses.RepoNode
import dataClasses.FolderNode
import dataClasses.FileNode
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parseAndSortContentTypeHeader
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

    suspend fun fetchFolder(owner: String, repo: String, rs: RepositoryStructure): FolderNode {
        val url = "https://api.github.com/repos/$owner/$repo/contents/${rs.path}"
        val childrenRs: List<RepositoryStructure> = client.get(url).body()

        val folder = FolderNode(
            name = rs.name,
            path = rs.path,
        )

        for (child in childrenRs) {
            val node = if (child.type == "dir") {
                fetchFolder(owner, repo, child)
            } else {
                fetchFile(owner, repo, child)
            }
            folder.children.add(node)
        }

        return folder
    }

    suspend fun fetchFile(owner: String, repo: String, rs: RepositoryStructure): FileNode {
        return FileNode(
            name = rs.name,
            path = rs.path,
            content = null,
        )
    }

    suspend fun fetchFileContents(owner: String, repo: String, path: String): String {
        val url = "https://api.github.com/repos/$owner/$repo/contents/$path"
        val file: RepositoryStructure = client.get(url).body()

        val base64 = file.content ?: return ""

        val cleaned = base64.filterNot { it.isWhitespace() }
        val decodedBytes = Base64.getDecoder().decode(cleaned)

        return decodedBytes.toString(Charsets.UTF_8)
    }

    suspend fun fetchRepo(owner: String, repo: String): FolderNode {
        /*
        Fetch all repository nodes.
        */
        val url = "https://api.github.com/repos/$owner/$repo/contents"
        val rootRs: List<RepositoryStructure> = client.get(url).body()

        val root = FolderNode(
            name = repo,
            path = "",
            children = mutableListOf()
        )

        for (item in rootRs) {
            val node = if (item.type == "dir") {
                fetchFolder(owner, repo, item)
            } else {
                fetchFile(owner, repo, item)
            }
            root.children.add(node)
        }

        return root
    }

    fun closeClient() {
        client.close()
    }

}