package feature.characters.screen.details.components.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.RickAndMortyTheme
import feature.characters.R

@Composable
internal fun CharacterDetailsEmptyState(
    modifier: Modifier = Modifier,
    visible: Boolean,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.message_character_empty_details),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                ),
            )
        }
    }
}

@Preview
@Composable
private fun CharacterDetailsEmptyStatePreview() {
    RickAndMortyTheme {
        CharacterDetailsEmptyState(visible = true)
    }
}
