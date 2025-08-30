package feature.characters.screen.details

import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviNavEvent
import arch.mvi.MviSideEffect
import feature.characters.model.CharacterDetailsUiMapper
import feature.characters.model.CharacterUi
import feature.characters.screen.details.mvi.CharacterDetailsIntent
import feature.characters.screen.details.mvi.CharacterDetailsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class CharacterDetailsViewModel @Inject constructor(
    private val characterDetailsUiMapper: CharacterDetailsUiMapper
) : BaseSimpleMviViewModel<CharacterDetailsViewState, CharacterDetailsIntent, MviSideEffect, MviNavEvent>(
    initialState = CharacterDetailsViewState()
) {

    override val tag: String = this.javaClass.simpleName

    override suspend fun executeIntent(mviIntent: CharacterDetailsIntent) = when (mviIntent) {
        is CharacterDetailsIntent.OnViewStarted -> onViewStarted(character = mviIntent.character)
    }

    private fun onViewStarted(character: CharacterUi) {
        updateUiState { oldState ->
            oldState.copy(
                characterAvatarUrl = character.imageUrl,
                characterData = characterDetailsUiMapper(character),
            )
        }
    }
}
