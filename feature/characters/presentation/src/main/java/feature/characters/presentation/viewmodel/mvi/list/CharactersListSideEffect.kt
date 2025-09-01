package feature.characters.presentation.viewmodel.mvi.list

import arch.mvi.MviSideEffect

sealed class CharactersListSideEffect : MviSideEffect {

    data object ShowErrorMessage : CharactersListSideEffect()

    data object ScrollToTop : CharactersListSideEffect()
}
