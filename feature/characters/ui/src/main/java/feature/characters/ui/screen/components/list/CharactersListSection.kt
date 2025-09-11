package feature.characters.ui.screen.components.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.senyk.rickandmorty.feature.characters.ui.R
import core.ui.animations.visibility.FadeAnimatedVisibility
import core.ui.animations.visibility.ScaleAnimatedVisibility
import core.ui.components.emptystate.SimpleEmptyState
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.RickAndMortyTheme
import core.ui.utils.widthWithCoef
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.viewmodel.mvi.list.CharactersListViewState
import feature.characters.ui.screen.components.list.grid.CharactersGrid
import feature.characters.ui.screen.preview.CharactersListPreviewParameterProvider

@Composable
internal fun BoxScope.CharactersListSection(
    viewState: CharactersListViewState,
    gridState: LazyGridState,
    onItemClicked: (item: CharacterUi) -> Unit,
    onRefreshed: () -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    CharactersGrid(
        gridState = gridState,
        loadingNextDataSet = viewState.showPaginationProgress,
        isRefreshing = viewState.showRefreshProgress,
        items = viewState.charactersList,
        onItemClicked = onItemClicked,
        onRefreshed = onRefreshed,
        onScrolled = onScrolled,
    )
    ScaleAnimatedVisibility(
        visible = viewState.showEmptyState,
        modifier = Modifier
            .width(widthWithCoef(0.6f))
            .align(Alignment.Center)
    ) {
        CharactersListEmptyState()
    }
    FadeAnimatedVisibility(visible = viewState.showBlockingProgress) {
        SimpleCircularProgress(blocking = true)
    }
}

@Composable
private fun CharactersListEmptyState(modifier: Modifier = Modifier) {
    SimpleEmptyState(
        textMessage = stringResource(R.string.message_empty_state_characters_list),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
        ),
        modifier = modifier
    )
}

@Preview
@Composable
private fun CharactersListSectionPreview(
    @PreviewParameter(CharactersListPreviewParameterProvider::class) viewState: CharactersListViewState
) {
    RickAndMortyTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            CharactersListSection(
                viewState = viewState,
                gridState = rememberLazyGridState(),
                onItemClicked = {},
                onRefreshed = {},
                onScrolled = {},
            )
        }
    }
}
