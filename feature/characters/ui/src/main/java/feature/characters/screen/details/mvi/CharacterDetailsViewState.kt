package feature.characters.screen.details.mvi

import arch.mvi.ViewState
import feature.characters.model.CharacterDetailsUi

internal data class CharacterDetailsViewState(
    val character: CharacterDetailsUi? = null,
    val isLoading: Boolean = true,
) : ViewState
