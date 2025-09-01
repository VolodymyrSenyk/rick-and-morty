package feature.characters.screen.details.mvi

import arch.mvi.MviIntent

internal sealed class CharacterDetailsIntent : MviIntent {

    data class OnViewStarted(val characterId: String) : CharacterDetailsIntent()

    data object OnBackButtonClicked : CharacterDetailsIntent()
}
