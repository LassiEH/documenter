import kotlinx.serialization.Serializable

/**
 * Represents a single piece of a document.
 */
@Serializable
sealed class DocumentItem {
    @Serializable
    data class Heading(val text: String) : DocumentItem()
    @Serializable
    data class Paragraph(val text: String) : DocumentItem()
    @Serializable
    data class Image(
        val fileName: String,
        val relativePath: String, // saving to json with bytearray is not sustainable
        //val bytes: ByteArray
    ) : DocumentItem()

    /**
     * A code snippet
     *
     * @property filePath: path of the source file in the repository
     * @property code: selected lines of code
     */
    @Serializable
    data class Code(val filePath: String, val code: String) : DocumentItem()
}