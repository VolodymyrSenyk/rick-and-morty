package feature.characters.presentation.viewmodel.mvi.details

import arch.mvi.MviSideEffect

sealed class CharacterDetailsSideEffect : MviSideEffect {

    data object ShowErrorMessage : CharacterDetailsSideEffect()
}
