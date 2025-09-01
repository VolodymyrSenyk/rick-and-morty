package feature.characters.screen.details.mvi

import arch.mvi.ViewState
import feature.characters.model.CharacterUi

internal data class CharacterDetailsViewState(
    val character: CharacterUi = CharacterUi.EMPTY,
) : ViewState
