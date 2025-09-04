package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersListViewState(
    val charactersList: List<CharacterUi>,
    val showEmptyState: Boolean,
    val showRefreshProgress: Boolean,
    val showBlockingProgress: Boolean,
    val showPaginationProgress: Boolean,
) : ViewState {

    companion object {
        val INITIAL = CharactersListViewState(
            charactersList = emptyList(),
            showEmptyState = false,
            showRefreshProgress = false,
            showBlockingProgress = true,
            showPaginationProgress = false,
        )
    }
}
