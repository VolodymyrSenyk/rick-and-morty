package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.MviNavEvent

sealed class CharacterDetailsNavEvent : MviNavEvent {

    data class NavigateToImageViewer(
        val sharedTransitionKey: String,
        val imageUrl: String,
        val characterName: String,
    ) : CharacterDetailsNavEvent()

    data object NavigateBack : CharacterDetailsNavEvent()
}
