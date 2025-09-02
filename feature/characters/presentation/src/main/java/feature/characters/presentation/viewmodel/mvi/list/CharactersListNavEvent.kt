package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.MviNavEvent
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.CharactersListFilter

sealed class CharactersListNavEvent : MviNavEvent {

    data class NavigateToCharacterDetails(val character: CharacterUi) : CharactersListNavEvent()

    data class NavigateToCharactersListFilter(val filter: CharactersListFilter) : CharactersListNavEvent()

    data object NavigateBack : CharactersListNavEvent()
}
