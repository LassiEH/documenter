package github

import dataClasses.RepositoryStructure
import dataClasses.FolderNode
import dataClasses.FileNode
import dataClasses.RepoNode
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import java.util.Base64

// https://docs.github.com/en/rest/repos/contents?apiVersion=2022-11-28
/**
 * RepoHandler is responsible for fetching repository data from
 * GitHub REST API
 */

class RepoHandler {

    private val client = HttpClient(CIO) {
        // https://ktor.io/docs/client-serialization.html#configure_serializer
        // configure serialization to allow JSON data deserialization
        install(ContentNegotiation) {
            json(Json{ ignoreUnknownKeys = true })
        }
    }

    /**
     * Recursively fetches a folder's contents and converts them into RepoNodes.
     * This is called by fetchRepo
     */
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

    /**
     * Creates a FileNode with content
     */
    suspend fun fetchFile(owner: String, repo: String, rs: RepositoryStructure): FileNode {
        // very heavy on GitHub api rate limits...
        val decoded = fetchFileContents(owner, repo, rs.path)
        return FileNode(
            name = rs.name,
            path = rs.path,
            content = decoded,
        )
    }

    /**
     * Decodes Base64 encoded file content returned by GitHub API.
     * This is called by fetchFileContents
     */
    fun decodeBase64(encodedContent: String?): String {
        val cleaned = encodedContent?.filterNot { it.isWhitespace() }
        val decodedBytes = Base64.getDecoder().decode(cleaned)

        return decodedBytes.toString(Charsets.UTF_8)
    }

    /**
     * Fetches file contents and passes them to decoder
     * before returning them.
     * This is called by fetchFile
     */
    suspend fun fetchFileContents(owner: String, repo: String, path: String): String {
        val url = "https://api.github.com/repos/$owner/$repo/contents/$path"
        val file: RepositoryStructure = client.get(url).body()

        val decodedString = decodeBase64(file.content)

        return decodedString
    }

    /**
     * Fetches the full repository structure and returns it as a FolderNode tree.
     * This fetches files and folders recursively, and fetches file contents eagerly.
     *
     * @param owner: GitHub username
     * @param repo: Name of the repository in GitHub
     * @return the root FolderNode
     */
    suspend fun fetchRepo(owner: String, repo: String): FolderNode {
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

    fun closeClient() {
        client.close()
    }

}