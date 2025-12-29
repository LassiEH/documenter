import kotlinx.serialization.Serializable

@Serializable
sealed class DocumentItem {
    @Serializable
    data class Heading(val text: String) : DocumentItem()
    @Serializable
    data class Paragraph(val text: String) : DocumentItem()
    @Serializable
    data class Image(val title: String, val bytes: ByteArray) : DocumentItem()
    @Serializable
    data class Code(val filePath: String, val code: String) : DocumentItem()
}