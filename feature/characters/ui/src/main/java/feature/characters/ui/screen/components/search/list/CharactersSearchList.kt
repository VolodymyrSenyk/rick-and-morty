package feature.characters.ui.screen.components.search.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import core.ui.components.progress.SimpleCircularProgress
import core.ui.theme.Dimens
import core.ui.theme.RickAndMortyTheme
import feature.characters.presentation.model.CharacterUi
import feature.characters.ui.screen.preview.CharactersPreviewMocks
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CharactersSearchList(
    modifier: Modifier = Modifier,
    listState: LazyListState,
    loadingNextDataSet: Boolean,
    items: List<CharacterUi>,
    onItemClicked: (item: CharacterUi) -> Unit,
    onScrolled: (lastVisibleItem: Int) -> Unit,
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .filterNotNull()
            .distinctUntilChanged()
            .collect { onScrolled(it) }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .collect { isScrolling ->
                if (isScrolling) {
                    keyboardController?.hide()
                }
            }
    }

    LazyColumn(
        state = listState,
        verticalArrangement = Arrangement.spacedBy(Dimens.Padding.Tiny),
        overscrollEffect = null,
        contentPadding = PaddingValues(Dimens.Padding.Tiny),
        modifier = modifier.fillMaxSize()
    ) {
        items(
            items = items,
            key = { item -> item.id },
        ) { listItem ->
            CharactersSearchListCard(
                item = listItem,
                onItemClicked = onItemClicked,
                modifier = Modifier.animateItem()
            )
        }
        if (loadingNextDataSet && items.isNotEmpty()) {
            item {
                SimpleCircularProgress(modifier = Modifier.scale(0.6f))
            }
        }
    }
}

@Preview
@Composable
private fun CharactersSearchListPreview() {
    RickAndMortyTheme {
        CharactersSearchList(
            listState = rememberLazyListState(),
            loadingNextDataSet = true,
            items = CharactersPreviewMocks.charactersList,
            onItemClicked = {},
            onScrolled = {},
        )
    }
}
