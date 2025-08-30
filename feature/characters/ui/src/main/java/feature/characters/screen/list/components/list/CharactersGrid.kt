package feature.characters.screen.list.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.model.CharacterUi
import feature.characters.preview.CharactersPreviewMocks
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharactersGrid(
    modifier: Modifier = Modifier,
    gridState: LazyGridState,
    isRefreshing: Boolean,
    loadingNextDataSet: Boolean,
    items: List<CharacterUi>,
    onItemClicked: (item: CharacterUi) -> Unit,
    onRefreshed: () -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { onScrolled(it) }
    }

    PullToRefreshBox(
        state = rememberPullToRefreshState(),
        isRefreshing = isRefreshing,
        onRefresh = onRefreshed,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(Dimens.Padding.Medium),
            horizontalArrangement = Arrangement.spacedBy(Dimens.Padding.Medium),
            overscrollEffect = null,
            contentPadding = PaddingValues(horizontal = Dimens.Padding.Medium, vertical = Dimens.Padding.Medium),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = items,
                key = { item -> item.listId },
            ) { listItem ->
                CharacterCard(
                    item = listItem,
                    onItemClicked = onItemClicked,
                    modifier = Modifier.animateItem()
                )
            }
            if (loadingNextDataSet) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    ProgressListItem()
                }
            }
        }
    }
}

@Preview
@Composable
private fun CharactersGridPreview() {
    RickAndMortyTheme {
        CharactersGrid(
            gridState = rememberLazyGridState(),
            isRefreshing = false,
            loadingNextDataSet = true,
            items = CharactersPreviewMocks.charactersList,
            onItemClicked = {},
            onRefreshed = {},
            onScrolled = {},
        )
    }
}
