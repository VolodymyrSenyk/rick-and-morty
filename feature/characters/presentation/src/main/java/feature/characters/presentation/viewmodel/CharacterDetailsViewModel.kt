package feature.characters.presentation.viewmodel

import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.usecase.GetCharacterByIdUseCase
import feature.characters.presentation.model.toCharacterDetailsUi
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
) : BaseSimpleMviViewModel<CharacterDetailsViewState, CharacterDetailsIntent, MviSideEffect, CharacterDetailsNavEvent>(
    initialState = CharacterDetailsViewState.INITIAL,
) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharacterDetailsIntent) = when (mviIntent) {
        is CharacterDetailsIntent.OnViewStarted -> onViewStarted(mviIntent.characterId)
        is CharacterDetailsIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    override suspend fun onError(throwable: Throwable) {
        updateUiState { oldState ->
            oldState.copy(
                showEmptyState = currentState.character == null,
                isLoading = false,
            )
        }
        super.onError(throwable)
    }

    private suspend fun onViewStarted(characterId: String) {
        val character = getCharacterByIdUseCase(characterId)
        updateUiState { oldState ->
            oldState.copy(
                character = character.toCharacterDetailsUi(),
                showEmptyState = false,
                isLoading = false,
            )
        }
    }

    private suspend fun onBackButtonClicked() {
        sendNavEvent(CharacterDetailsNavEvent.NavigateBack)
    }
}
