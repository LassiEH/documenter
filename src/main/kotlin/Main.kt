import github.RepoHandler


suspend fun main() {
    val owner = "LassiEH"
    val repo = "documenter"
    val contents = "src/main/kotlin"
    val fileAddress = "src/main/kotlin/Main.kt"
    val addList = mutableListOf<String>()
    val repoHandler = RepoHandler()

    val files = repoHandler.fetchRepoContents(owner, repo, contents)

    files.forEach {
        println(it)
        println(it.content)
        addList.add(it.url)
    }

    println(addList)
    println(addList[0])

    val fileText = repoHandler.fetchFileContents(owner, repo, addList[0])
    println(fileText)

}