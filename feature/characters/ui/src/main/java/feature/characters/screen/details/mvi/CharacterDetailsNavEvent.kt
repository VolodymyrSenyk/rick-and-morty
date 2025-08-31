package feature.characters.screen.details.mvi

import arch.mvi.MviNavEvent

internal sealed class CharacterDetailsNavEvent : MviNavEvent {

    data object NavigateBack : CharacterDetailsNavEvent()
}
