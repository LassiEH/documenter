package dataClasses

sealed class RepoNode {
    abstract val name: String
    abstract val path: String
}

data class FolderNode(
    override val name: String,
    override val path: String,
    val children: MutableList<RepoNode> = mutableListOf()
) : RepoNode()

data class FileNode(
    override val name: String,
    override val path: String,
    val content: String? = null,
) : RepoNode()