package feature.characters.screen.list.mvi

import arch.mvi.ViewState
import feature.characters.model.ListItem

internal data class CharactersListViewState(
    val charactersList: List<ListItem> = emptyList(),
    val isRefreshing: Boolean = false,
    val showProgress: Boolean = true,
    val loadingNextDataSet: Boolean = false,
) : ViewState
