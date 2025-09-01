package feature.characters.screen.details

import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import feature.characters.model.CharacterUi
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsNavEvent
import feature.characters.screen.details.mvi.CharacterDetailsViewState
import javax.inject.Inject

@HiltViewModel
internal class CharacterDetailsViewModel @Inject constructor() :
    BaseSimpleMviViewModel<CharacterDetailsViewState, CharacterDetailsIntent, MviSideEffect, CharacterDetailsNavEvent>(
        initialState = CharacterDetailsViewState()
    ) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharacterDetailsIntent) = when (mviIntent) {
        is CharacterDetailsIntent.OnViewStarted -> onViewStarted(character = mviIntent.character)
        is CharacterDetailsIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    private fun onViewStarted(character: CharacterUi) {
        updateUiState { oldState ->
            oldState.copy(character = character)
        }
    }

    private suspend fun onBackButtonClicked() {
        sendNavEvent(CharacterDetailsNavEvent.NavigateBack)
    }
}
