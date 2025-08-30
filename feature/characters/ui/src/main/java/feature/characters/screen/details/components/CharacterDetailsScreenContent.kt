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
import feature.characters.model.CharacterDetailsUiMapper
import feature.characters.screen.details.components.list.CharacterDetailsList
import feature.characters.screen.details.mvi.CharacterDetailsViewState
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.util.provider.ResourcesProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.Dimens

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
            model = viewState.characterAvatarUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ImageSize.Big)
                .padding(Dimens.Padding.Small),
        )
        CharacterDetailsList(items = viewState.characterData)
    }
}

@Preview
@Composable
private fun CharacterDetailsScreenContentPreview() {
    RickAndMortyTheme {
        val context = LocalContext.current
        val resourcesProvider = ResourcesProvider(context)
        val mapper = CharacterDetailsUiMapper(resourcesProvider)
        val characterDetails = mapper(CharactersPreviewMocks.character)
        CharacterDetailsScreenContent(
            viewState = CharacterDetailsViewState(
                characterAvatarUrl = CharactersPreviewMocks.avatarConstructor(),
                characterData = characterDetails,
            ),
        )
    }
}
