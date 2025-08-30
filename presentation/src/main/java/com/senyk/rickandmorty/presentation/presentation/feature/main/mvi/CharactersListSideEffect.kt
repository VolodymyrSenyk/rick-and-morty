package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.MviSideEffect

internal sealed class CharactersListSideEffect : MviSideEffect {

    data object ShowErrorMessage : CharactersListSideEffect()

    data object ScrollToTop : CharactersListSideEffect()
}
