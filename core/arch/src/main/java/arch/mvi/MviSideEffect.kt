package arch.mvi

/**
 * Represents a one-time UI side effect, such as showing a toast or snackbar.
 * Side effects do not alter the [ViewState], but affect UI transiently.
 */
interface MviSideEffect
