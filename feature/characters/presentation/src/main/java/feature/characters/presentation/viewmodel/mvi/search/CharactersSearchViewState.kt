package feature.characters.presentation.viewmodel.mvi.search

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersSearchViewState(
    val isSearching: Boolean,
    val searchQuery: String,
    val isInvalidSearchQuery: Boolean,
    val searchResults: List<CharacterUi>,
    val showEmptyState: Boolean,
    val showBlockingProgress: Boolean,
    val showPaginationProgress: Boolean,
) : ViewState {

    companion object {
        val INITIAL = CharactersSearchViewState(
            isSearching = false,
            searchQuery = "",
            isInvalidSearchQuery = true,
            searchResults = emptyList(),
            showEmptyState = false,
            showBlockingProgress = false,
            showPaginationProgress = false,
        )
    }
}
