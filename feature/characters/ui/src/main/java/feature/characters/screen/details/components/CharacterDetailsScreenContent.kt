package feature.characters.screen.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import core.ui.components.progress.BlockingProgress
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.screen.details.components.list.CharacterDetailsEmptyState
import feature.characters.screen.details.components.list.CharacterDetailsList
import feature.characters.screen.details.mvi.CharacterDetailsViewState

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
        AnimatedVisibility(
            visible = viewState.character != null,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Column(modifier = Modifier.padding(horizontal = Dimens.Padding.Small)) {
                val character = viewState.character ?: return@Column
                AsyncImage(
                    model = character.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimens.ImageSize.Big)
                        .padding(Dimens.Padding.Small),
                )
                CharacterDetailsList(character = character)
            }
        }
        CharacterDetailsEmptyState(visible = viewState.character == null && !viewState.isLoading)
        BlockingProgress(visible = viewState.isLoading, indicatorColor = MaterialTheme.colorScheme.primary)
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
