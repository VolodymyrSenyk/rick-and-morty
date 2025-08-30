package feature.characters.screen.details.mvi

import arch.mvi.MviIntent
import feature.characters.model.CharacterUi

internal sealed class CharacterDetailsIntent : MviIntent {

    data class OnViewStarted(val character: CharacterUi) : CharacterDetailsIntent()
}
