package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.MviIntent
import feature.characters.presentation.model.CharacterUi

sealed class CharacterDetailsIntent : MviIntent {

    data class OnViewStarted(val character: CharacterUi) : CharacterDetailsIntent()

    data object OnBackButtonClicked : CharacterDetailsIntent()
}
