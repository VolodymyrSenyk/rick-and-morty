package feature.characters.ui.screen.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import feature.characters.ui.R
import feature.characters.ui.screen.components.details.list.AdaptiveCharacterDetails
import feature.characters.ui.screen.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsScreenContent(
    modifier: Modifier = Modifier,
    viewState: CharacterDetailsViewState,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        AdaptiveCharacterDetails(
            visible = viewState.character != null,
            character = viewState.character,
        )
        SimpleEmptyState(
            visible = viewState.character == null && !viewState.isLoading,
            textMessage = stringResource(R.string.message_character_empty_details),
        )
        SimpleCircularProgress(
            visible = viewState.isLoading,
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenContentPreview() {
    RickAndMortyTheme {
        CharacterDetailsScreenContent(
            viewState = CharacterDetailsViewState(
                character = CharactersPreviewMocks.characterDetails,
                isLoading = false,
            )
        )
    }
}
