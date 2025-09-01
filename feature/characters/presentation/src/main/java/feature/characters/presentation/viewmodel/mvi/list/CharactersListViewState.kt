package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersListViewState(
    val charactersList: List<CharacterUi> = emptyList(),
    val isRefreshing: Boolean = false,
    val showProgress: Boolean = true,
    val loadingNextDataSet: Boolean = false,
) : ViewState
