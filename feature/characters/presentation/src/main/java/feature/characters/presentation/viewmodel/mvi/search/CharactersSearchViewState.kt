package feature.characters.presentation.viewmodel.mvi.search

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterUi

data class CharactersSearchViewState(
    val isSearching: Boolean = false,
    val searchQuery: String = "",
    val tooSmallSearchQuery: Boolean = true,
    val searchResults: List<CharacterUi> = emptyList(),
    val showProgress: Boolean = false,
    val loadingNextDataPage: Boolean = false,
) : ViewState
