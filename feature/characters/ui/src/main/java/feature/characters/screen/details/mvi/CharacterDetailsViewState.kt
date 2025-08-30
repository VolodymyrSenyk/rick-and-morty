package feature.characters.screen.details.mvi

import arch.mvi.ViewState
import feature.characters.model.CharacterDetailsUi

internal data class CharacterDetailsViewState(
    val characterAvatarUrl: String? = null,
    val characterData: List<CharacterDetailsUi> = emptyList(),
) : ViewState
