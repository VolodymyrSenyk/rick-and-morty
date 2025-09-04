package arch.mvi

/**
 * Marker interface for intents that should be debounced before execution.
 *
 * Intents implementing this interface will not be executed immediately.
 * Instead, they are collected and executed only after a specified debounce
 * period has passed without new emissions. This helps to prevent excessive
 * calls to expensive operations such as network requests (e.g. search queries).
 *
 * `DebounceableIntent` **must not** be defined as an `object`.
 * Use a `data class` or a regular class that contains mutable data.
 * Otherwise, `debounce` and `distinctUntilChanged` would treat all events as identical,
 * preventing new values from being emitted correctly.
 */
interface DebounceableIntent : MviIntent
