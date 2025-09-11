package feature.characters.ui.screen.components.list

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.animations.visibility.SlideTopToBottomAnimatedVisibility
import core.ui.components.scaffold.CustomScaffold
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState
import feature.characters.ui.screen.components.search.CharactersSearchSection
import feature.characters.ui.screen.components.search.CharactersSearchTopAppBar
import feature.characters.ui.screen.preview.CharactersListPreviewParameterProvider

@Composable
internal fun CharactersListScreenContent(
    viewState: CharactersListViewState,
    searchViewState: CharactersSearchViewState,
    gridState: LazyGridState,
    onSearchClicked: () -> Unit,
    onSearchQueryChanged: (searchQuery: String) -> Unit,
    onFilterClicked: () -> Unit,
    onThemeSelected: (newThemeMode: ThemeMode) -> Unit,
    onCharacterClicked: (item: CharacterUi) -> Unit,
    onRefreshed: () -> Unit,
    onCharactersGridScrolled: (lastVisibleItem: Int) -> Unit,
    onSearchResultsScrolled: (lastVisibleItem: Int) -> Unit,
) {
    val searchEnterAnimMillis = 400
    CustomScaffold(
        topAppBar = {
            if (searchViewState.isSearching) {
                CharactersSearchTopAppBar(
                    searchQuery = searchViewState.searchQuery,
                    onSearchQueryChange = onSearchQueryChanged,
                    onSearchToggle = onSearchClicked,
                    enterAnimMillis = searchEnterAnimMillis.toLong(),
                )
            } else {
                CharactersListTopAppBar(
                    onThemeSelected = onThemeSelected,
                    onSearchClicked = onSearchClicked,
                    onFilterClicked = onFilterClicked,
                )
            }
        }
    ) {
        CharactersListSection(
            gridState = gridState,
            viewState = viewState,
            onItemClicked = onCharacterClicked,
            onRefreshed = onRefreshed,
            onScrolled = onCharactersGridScrolled,
        )
        SlideTopToBottomAnimatedVisibility(
            visible = searchViewState.isSearching,
            durationMillis = searchEnterAnimMillis,
        ) {
            CharactersSearchSection(
                listState = rememberLazyListState(),
                viewState = searchViewState,
                onItemClicked = onCharacterClicked,
                onScrolled = onSearchResultsScrolled,
            )
        }
    }
}

@Preview
@Composable
private fun CharactersListScreenContentPreview(
    @PreviewParameter(CharactersListPreviewParameterProvider::class) viewState: CharactersListViewState
) {
    RickAndMortyTheme {
        CharactersListScreenContent(
            viewState = viewState,
            searchViewState = CharactersSearchViewState.INITIAL,
            gridState = rememberLazyGridState(),
            onSearchClicked = {},
            onSearchQueryChanged = {},
            onFilterClicked = {},
            onThemeSelected = {},
            onCharacterClicked = {},
            onRefreshed = {},
            onCharactersGridScrolled = {},
            onSearchResultsScrolled = {},
        )
    }
}
