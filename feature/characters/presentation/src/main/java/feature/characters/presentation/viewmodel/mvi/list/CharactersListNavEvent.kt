package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.MviNavEvent
import feature.characters.presentation.model.CharacterUi

sealed class CharactersListNavEvent : MviNavEvent {

    data class NavigateToCharacterDetails(val character: CharacterUi) : CharactersListNavEvent()

    data object NavigateBack : CharactersListNavEvent()
}
