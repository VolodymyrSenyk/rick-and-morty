package feature.characters.screen.list.mvi

import arch.mvi.MviNavEvent
import feature.characters.model.CharacterUi

internal sealed class CharactersListNavEvent : MviNavEvent {

    data class NavigateToCharacterDetails(val character: CharacterUi) : CharactersListNavEvent()

    data object NavigateBack : CharactersListNavEvent()
}
