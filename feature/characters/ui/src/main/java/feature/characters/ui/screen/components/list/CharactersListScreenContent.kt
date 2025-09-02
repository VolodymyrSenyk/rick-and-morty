package feature.characters.ui.screen.components.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import feature.characters.ui.R
import feature.characters.ui.screen.components.list.grid.CharactersGrid
import feature.characters.ui.screen.preview.CharactersListPreviewParameterProvider

@Composable
internal fun CharactersListScreenContent(
    modifier: Modifier = Modifier,
    viewState: CharactersListViewState,
    gridState: LazyGridState,
    onItemClicked: (item: CharacterUi) -> Unit,
    onRefreshed: () -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        AnimatedVisibility(
            visible = viewState.charactersList.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            CharactersGrid(
                gridState = gridState,
                loadingNextDataSet = viewState.isLoadingNextPage,
                isRefreshing = viewState.isRefreshing,
                items = viewState.charactersList,
                onItemClicked = onItemClicked,
                onRefreshed = onRefreshed,
                onScrolled = onScrolled,
            )
        }
        SimpleEmptyState(
            visible = viewState.charactersList.isEmpty() && !viewState.isLoading,
            textMessage = stringResource(R.string.message_characters_empty_list),
        )
        SimpleCircularProgress(
            visible = viewState.isLoading,
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f))
        )
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
            gridState = rememberLazyGridState(),
            onItemClicked = {},
            onRefreshed = {},
            onScrolled = {},
        )
    }
}
