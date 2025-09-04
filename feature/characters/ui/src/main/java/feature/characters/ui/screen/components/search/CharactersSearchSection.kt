package feature.characters.ui.screen.components.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.zIndex
import core.ui.animation.visibility.FadeAnimatedVisibility
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.search.CharactersSearchViewState
import feature.characters.ui.R
import feature.characters.ui.screen.components.search.list.CharactersSearchList
import feature.characters.ui.screen.preview.CharactersSearchPreviewParameterProvider

@Composable
internal fun CharactersSearchSection(
    viewState: CharactersSearchViewState,
    listState: LazyListState,
    onItemClicked: (item: CharacterUi) -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surfaceVariant)
            .zIndex(1f)
    ) {
        CharactersSearchList(
            listState = listState,
            loadingNextDataSet = viewState.showPaginationProgress,
            items = viewState.searchResults,
            onItemClicked = onItemClicked,
            onScrolled = onScrolled,
        )
        FadeAnimatedVisibility(visible = viewState.showEmptyState) {
            CharactersSearchResultsListEmptyState()
        }
        FadeAnimatedVisibility(visible = viewState.showBlockingProgress) {
            SimpleCircularProgress(blocking = true, backgroundColor = Color.Transparent)
        }
    }
}

@Composable
private fun CharactersSearchResultsListEmptyState(modifier: Modifier = Modifier) {
    SimpleEmptyState(
        textMessage = stringResource(R.string.message_empty_state_characters_list),
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
        ),
        modifier = modifier.padding(
            vertical = Dimens.Padding.Medium,
            horizontal = Dimens.Padding.Medium,
        )
    )
}

@Preview
@Composable
private fun CharactersSearchSectionPreview(
    @PreviewParameter(CharactersSearchPreviewParameterProvider::class) viewState: CharactersSearchViewState
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
