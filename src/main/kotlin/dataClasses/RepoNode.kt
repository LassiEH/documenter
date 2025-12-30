package dataClasses

/**
 * RepoNode represents a folder or a file
 * inside a GitHub repository
 */
sealed class RepoNode {
    abstract val name: String
    abstract val path: String
}

/**
 * Represents a folder.
 * The children can be other folders or files.
 * The tree structure mirrors the repository layout in GitHub
 */
data class FolderNode(
    override val name: String,
    override val path: String,
    val children: MutableList<RepoNode> = mutableListOf()
) : RepoNode()

/**
 * Represents a file
 */
data class FileNode(
    override val name: String,
    override val path: String,
    val content: String? = null,
) : RepoNode()