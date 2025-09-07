package core.ui.components.emptystate

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme

@Composable
fun SimpleEmptyState(
    modifier: Modifier = Modifier,
    textMessage: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
) {
    Text(
        text = textMessage,
        textAlign = TextAlign.Center,
        style = textStyle,
        modifier = modifier
    )
}

@Preview
@Composable
private fun SimpleEmptyStatePreview() {
    RickAndMortyTheme {
        SimpleEmptyState(textMessage = "Empty state")
    }
}
