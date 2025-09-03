package feature.characters.presentation.viewmodel.mvi.search

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersSearchViewState(
    val isSearching: Boolean,
    val searchQuery: String,
    val isInvalidSearchQuery: Boolean,
    val searchResults: List<CharacterUi>,
    val isLoading: Boolean,
    val isLoadingNextPage: Boolean,
) : ViewState {

    companion object {
        val INITIAL = CharactersSearchViewState(
            isSearching = false,
            searchResults = emptyList(),
            searchQuery = "",
            isInvalidSearchQuery = true,
            isLoading = false,
            isLoadingNextPage = false,
        )
    }
}
