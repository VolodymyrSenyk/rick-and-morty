package feature.characters.screen.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.model.toCharacterDetailsUiList
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.screen.details.components.list.CharacterDetailsList
import feature.characters.screen.details.mvi.CharacterDetailsViewState

@Composable
internal fun CharacterDetailsScreenContent(
    modifier: Modifier = Modifier,
    viewState: CharacterDetailsViewState,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = Dimens.Padding.Small)
    ) {
        AsyncImage(
            model = viewState.character.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ImageSize.Big)
                .padding(Dimens.Padding.Small),
        )
        CharacterDetailsList(items = viewState.character.toCharacterDetailsUiList(LocalContext.current))
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenContentPreview() {
    RickAndMortyTheme {
        CharacterDetailsScreenContent(viewState = CharacterDetailsViewState(character = CharactersPreviewMocks.character))
    }
}
