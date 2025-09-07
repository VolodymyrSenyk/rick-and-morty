package feature.characters.presentation.viewmodel

import arch.android.BaseSimpleMviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.characters.usecase.GetCharacterByIdUseCase
import feature.characters.presentation.model.CharacterUi
import feature.characters.presentation.model.toCharacterDetailsUi
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsIntent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsNavEvent
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsSideEffect
import feature.characters.presentation.viewmodel.mvi.details.CharacterDetailsViewState
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
) : BaseSimpleMviViewModel<CharacterDetailsViewState, CharacterDetailsIntent, CharacterDetailsSideEffect, CharacterDetailsNavEvent>(
    initialState = CharacterDetailsViewState.INITIAL,
) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharacterDetailsIntent) = when (mviIntent) {
        is CharacterDetailsIntent.OnViewStarted -> onViewStarted(mviIntent.character)
        is CharacterDetailsIntent.OnBackButtonClicked -> onBackButtonClicked()
    }

    private suspend fun onViewStarted(character: CharacterUi) {
        updateUiState { oldState ->
            oldState.copy(character = character.toCharacterDetailsUi())
        }
        val updatedCharacter = getCharacterByIdUseCase(character.id)
        updateUiState { oldState ->
            oldState.copy(character = updatedCharacter.toCharacterDetailsUi(uiId = character.uiId))
        }
    }

    private suspend fun onBackButtonClicked() {
        sendNavEvent(CharacterDetailsNavEvent.NavigateBack)
    }

    override suspend fun onError(throwable: Throwable) {
        sendSideEffect(CharacterDetailsSideEffect.ShowErrorMessage)
        super.onError(throwable)
    }
}
