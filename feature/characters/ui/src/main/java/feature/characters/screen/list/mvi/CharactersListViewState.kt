package feature.characters.screen.list.mvi

import arch.mvi.ViewState
import feature.characters.model.CharacterUi

internal data class CharactersListViewState(
    val charactersList: List<CharacterUi> = emptyList(),
    val isRefreshing: Boolean = false,
    val showProgress: Boolean = true,
    val loadingNextDataSet: Boolean = false,
) : ViewState
