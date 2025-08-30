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
import feature.characters.model.CharacterDetailsUi
import feature.characters.model.CharacterDetailsUiMapper
import feature.characters.preview.CharactersPreviewMocks
import feature.characters.util.provider.ResourcesProvider
import core.ui.theme.RickAndMortyTheme
import core.ui.theme.Dimens

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
        val context = LocalContext.current
        val resourcesProvider = ResourcesProvider(context)
        val mapper = CharacterDetailsUiMapper(resourcesProvider)
        val characterDetails = mapper(CharactersPreviewMocks.character)
        CharacterDetailsList(items = characterDetails)
    }
}
