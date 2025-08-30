package feature.characters.screen.list.components

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import core.ui.components.progress.BlockingProgress
import core.ui.theme.RickAndMortyTheme
import feature.characters.model.CharacterUi
import feature.characters.preview.CharactersListViewStatePreviewParameterProvider
import feature.characters.screen.list.components.list.CharactersGrid
import feature.characters.screen.list.components.list.CharactersListEmptyState
import feature.characters.screen.list.mvi.CharactersListViewState

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
                loadingNextDataSet = viewState.loadingNextDataSet,
                isRefreshing = viewState.isRefreshing,
                items = viewState.charactersList.filterIsInstance<CharacterUi>(),
                onItemClicked = onItemClicked,
                onRefreshed = onRefreshed,
                onScrolled = onScrolled,
            )
        }

        AnimatedVisibility(
            visible = viewState.charactersList.isEmpty() && !viewState.showProgress,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            CharactersListEmptyState()
        }

        BlockingProgress(
            visible = viewState.showProgress,
            modifier = Modifier.background(color = Color.Black.copy(alpha = 0.5f))
        )
    }
}

@Preview
@Composable
private fun CharactersListScreenContentPreview(
    @PreviewParameter(CharactersListViewStatePreviewParameterProvider::class) viewState: CharactersListViewState
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
