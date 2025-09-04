package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.ViewState
import feature.characters.presentation.model.CharacterDetailsUi

data class CharacterDetailsViewState(
    val character: CharacterDetailsUi?,
    val showEmptyState: Boolean,
    val isLoading: Boolean,
) : ViewState {

    companion object {
        val INITIAL = CharacterDetailsViewState(
            character = null,
            showEmptyState = false,
            isLoading = true,
        )
    }
}
