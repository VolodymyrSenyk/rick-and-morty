package feature.characters.screen.list.mvi

import arch.mvi.MviSideEffect

internal sealed class CharactersListSideEffect : MviSideEffect {

    data object ShowErrorMessage : CharactersListSideEffect()

    data object ScrollToTop : CharactersListSideEffect()
}
