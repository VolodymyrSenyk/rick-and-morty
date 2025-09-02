package feature.characters.presentation.viewmodel.mvi.search

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersSearchViewState(
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val isInvalidSearchQuery: Boolean = true,
    val searchResults: List<CharacterUi> = emptyList(),
    val isLoading: Boolean = false,
    val isLoadingNextPage: Boolean = false,
) : ViewState
