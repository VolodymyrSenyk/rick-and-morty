package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersListViewState(
    val charactersList: List<CharacterUi>,
    val isRefreshing: Boolean,
    val isLoading: Boolean,
    val isLoadingNextPage: Boolean,
) : ViewState {

    companion object {
        val INITIAL = CharactersListViewState(
            charactersList = emptyList(),
            isRefreshing = false,
            isLoading = true,
            isLoadingNextPage = false,
        )
    }
}
