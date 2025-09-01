package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.MviNavEvent

sealed class CharacterDetailsNavEvent : MviNavEvent {

    data object NavigateBack : CharacterDetailsNavEvent()
}
