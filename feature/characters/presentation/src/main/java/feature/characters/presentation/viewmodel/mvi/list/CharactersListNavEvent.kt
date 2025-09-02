package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.MviNavEvent
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.CharactersListFilterSettings

sealed class CharactersListNavEvent : MviNavEvent {

    data class NavigateToCharacterDetails(val character: CharacterUi) : CharactersListNavEvent()

    data class NavigateToFilterSettings(val filterSettings: CharactersListFilterSettings) : CharactersListNavEvent()

    data object NavigateBack : CharactersListNavEvent()
}
