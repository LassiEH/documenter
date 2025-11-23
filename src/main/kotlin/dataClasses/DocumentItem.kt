sealed class DocumentItem {
    data class Heading(val text: String) : DocumentItem()
    data class Paragraph(val text: String) : DocumentItem()
    data class Image(val title: String, val bytes: ByteArray) : DocumentItem()
    data class Code(val title: String) : DocumentItem()
}