package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dataClasses.Document
import dataClasses.RepoNode

@Composable
fun CodeBlock(code: DocumentItem.Code) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE0E0E0))
            .padding(16.dp)
    ) {
        Text(
            text = code.code,
            fontFamily = FontFamily.Monospace,
        )
    }
}

@Composable
fun DocumenterView(document: Document) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = document.title,
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (item in document.parts) {
            when (item) {
                is DocumentItem.Heading -> {
                    Text(
                        text = item.text,
                        style = MaterialTheme.typography.h6
                    )
                }
                is DocumentItem.Paragraph -> {
                    Text(
                        text = item.text,
                    )
                }
                is DocumentItem.Code -> {
                    CodeBlock(item)
                }
                is DocumentItem.Image -> {
                    Text(item.title)
                }
            }
        }
    }

}