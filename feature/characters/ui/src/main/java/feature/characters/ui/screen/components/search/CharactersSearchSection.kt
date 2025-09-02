package feature.characters.ui.screen.components.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.zIndex
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState
import feature.characters.ui.R
import feature.characters.ui.screen.components.search.list.CharactersSearchList
import feature.characters.ui.screen.preview.CharactersSearchViewStatePreviewParameterProvider

@Composable
internal fun CharactersSearchSection(
    modifier: Modifier = Modifier,
    viewState: CharactersSearchViewState,
    listState: LazyListState,
    onItemClicked: (item: CharacterUi) -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    AnimatedVisibility(
        visible = viewState.isSearching,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it }),
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .zIndex(1f)
    ) {
        CharactersSearchList(
            listState = listState,
            loadingNextDataSet = viewState.loadingNextDataPage,
            items = viewState.searchResults,
            onItemClicked = onItemClicked,
            onScrolled = onScrolled,
        )
        SimpleEmptyState(
            visible = viewState.searchResults.isEmpty() && !viewState.showProgress,
            textMessage = if (viewState.tooSmallSearchQuery) {
                stringResource(R.string.message_empty_state_search)
            } else {
                stringResource(R.string.message_characters_empty_list)
            },
        )
        SimpleCircularProgress(
            visible = viewState.showProgress,
            indicatorColor = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview
@Composable
private fun CharactersSearchSectionPreview(
    @PreviewParameter(CharactersSearchViewStatePreviewParameterProvider::class) viewState: CharactersSearchViewState
) {
    RickAndMortyTheme {
        CharactersSearchSection(
            viewState = viewState,
            listState = rememberLazyListState(),
            onItemClicked = {},
            onScrolled = {},
        )
    }
}
