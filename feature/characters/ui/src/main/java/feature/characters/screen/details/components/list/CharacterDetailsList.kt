package feature.characters.screen.details.components.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.model.CharacterDetailsUi
import feature.characters.model.toCharacterDetailsUiList
import feature.characters.preview.CharactersPreviewMocks

@Composable
internal fun CharacterDetailsList(
    modifier: Modifier = Modifier,
    items: List<CharacterDetailsUi>,
) {
    LazyColumn(
        state = rememberLazyListState(),
        contentPadding = PaddingValues(vertical = Dimens.Padding.Tiny),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = items,
            key = { item -> item.label },
        ) { item ->
            CharacterDetailsItem(item = item)
        }
    }
}

@Preview
@Composable
private fun RulesListPreview() {
    RickAndMortyTheme {
        CharacterDetailsList(items = CharactersPreviewMocks.character.toCharacterDetailsUiList(LocalContext.current))
    }
}
