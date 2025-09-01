package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.MviIntent

sealed class CharacterDetailsIntent : MviIntent {

    data class OnViewStarted(val characterId: String) : CharacterDetailsIntent()

    data object OnBackButtonClicked : CharacterDetailsIntent()
}
