import github.RepoHandler


suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val fileAddress = "src/main/kotlin/Main.kt"

    val repoHandler = RepoHandler()

    val files = repoHandler.fetchRepoContents(owner, repo, contents)

    files.forEach {
        println(it)
        println(it.download_url)
    }

    val fileText = repoHandler.fetchFileContents(owner, repo, fileAddress)
    println(fileText)

}