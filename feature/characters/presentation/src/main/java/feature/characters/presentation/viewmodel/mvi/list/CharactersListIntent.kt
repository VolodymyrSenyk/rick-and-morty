package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.MviIntent
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.CharactersListFilterSettings

sealed class CharactersListIntent : MviIntent {

    data object OnViewStarted : CharactersListIntent()

    data class OnScrolled(val lastVisibleItemPosition: Int) : CharactersListIntent()

    data object OnRefreshed : CharactersListIntent()

    data class OnCharacterClicked(val character: CharacterUi) : CharactersListIntent()

    data object OnFilterClicked : CharactersListIntent()

    data class OnFilterApplied(val filterSettings: CharactersListFilterSettings) : CharactersListIntent()

    data object OnBackButtonClicked : CharactersListIntent()
}
