package feature.characters.ui.screen.components.details.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme

@Composable
internal fun CharacterDetailsItem(
    modifier: Modifier = Modifier,
    label: String,
    data: String,
) {
    if (label.isBlank() || data.isBlank()) return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.Padding.Tiny)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
            ),
        )

        Spacer(Modifier.width(Dimens.Padding.Small))

        Text(
            text = data,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        )
    }
}

@Preview
@Composable
private fun CharacterDetailsItemPreview() {
    RickAndMortyTheme {
        CharacterDetailsItem(label = "Name:", data = "Rick Sanchez")
    }
}
