package feature.characters.presentation.viewmodel.mvi.search

import arch.mvi.MviIntent

sealed class CharactersSearchIntent : MviIntent {

    data object OnSearchToggle : CharactersSearchIntent()

    data class OnSearchQueryChanged(val searchQuery: String) : CharactersSearchIntent()

    data class OnScrolled(val lastVisibleItemPosition: Int) : CharactersSearchIntent()
}
