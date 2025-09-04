package arch.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arch.log.Kermit
import arch.mvi.DebounceableIntent
import arch.mvi.MviIntent
import arch.mvi.MviNavEvent
import arch.mvi.MviSideEffect
import arch.mvi.ViewState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * A base implementation of a simple MVI (Model-View-Intent) [ViewModel].
 *
 * This class handles:
 * - **State management** using [StateFlow]
 * - **One-time side effects** using [StateFlow]
 * - **Navigation events** using [StateFlow]
 * - **Intent processing** through a buffered [MutableSharedFlow]
 *
 * Subclasses are expected to:
 * - Provide an initial state
 * - Implement [executeIntent] to handle incoming [MviIntent]s
 * - Optionally override [onError] to handle unexpected errors
 *
 * @param S The type representing the [ViewState]
 * @param I The type representing the [MviIntent]
 * @param E The type representing the [MviSideEffect]
 * @param N The type representing the [MviNavEvent]
 * @property debounceTimeoutMillis The debounce interval in milliseconds for [DebounceableIntent]s
 * @property initialState The initial UI state of the ViewModel
 * @property flowsBufferCapacity The number of additional intents that can be buffered
 */
abstract class BaseSimpleMviViewModel<S : ViewState, I : MviIntent, E : MviSideEffect, N : MviNavEvent>(
    private val debounceTimeoutMillis: Long = DEFAULT_DEBOUNCE_TIMEOUT_MILLIS,
    initialState: S,
    flowsBufferCapacity: Int = DEFAULT_FLOW_BUFFER_CAPACITY,
) : ViewModel() {

    /**
     * The current immutable [StateFlow] representing the UI state.
     */
    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<S> = _uiState.asStateFlow()
    protected val currentState: S
        get() = uiState.value

    /**
     * The current one-time [StateFlow] representing UI side effects.
     */
    private val _sideEffect = MutableStateFlow<E?>(null)
    val sideEffect: StateFlow<E?> = _sideEffect.asStateFlow()

    /**
     * The current one-time [StateFlow] representing navigation events.
     */
    private val _navEvent = MutableStateFlow<N?>(null)
    val navEvent: StateFlow<N?> = _navEvent.asStateFlow()

    private val intentFlow = MutableSharedFlow<I>(extraBufferCapacity = flowsBufferCapacity)

    /**
     * Log tag for debugging purposes.
     */
    protected abstract val tag: String

    /**
     * The [CoroutineScope] in which intents and other asynchronous operations are executed.
     *
     * This scope is set once via [setCoroutineScope] (typically from the platform-specific ViewModel).
     * It should be tied to the lifecycle of the owner (e.g., viewModelScope on Android) to ensure
     * proper cancellation of running coroutines.
     */
    protected lateinit var coroutineScope: CoroutineScope
        private set

    // Start listening to intents immediately in the ViewModel scope
    init {
        setCoroutineScope(viewModelScope)
    }

    /**
     * Sets the [CoroutineScope] for intent processing and starts collecting intents.
     * If not provided, a new scope with [Dispatchers.IO] is used.
     * @param coroutineScope The scope in which intents will be collected and executed.
     */
    @OptIn(FlowPreview::class)
    fun setCoroutineScope(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
        this.coroutineScope = coroutineScope
        intentFlow
            .filter { it !is DebounceableIntent }
            .onEach { intent ->
                try {
                    executeIntent(intent)
                } catch (throwable: Throwable) {
                    onError(throwable)
                }
            }
            .launchIn(coroutineScope)
        intentFlow
            .filterIsInstance<DebounceableIntent>()
            .debounce(debounceTimeoutMillis)
            .distinctUntilChanged()
            .onEach { intent ->
                try {
                    @Suppress("UNCHECKED_CAST")
                    executeIntent(intent as I)
                } catch (throwable: Throwable) {
                    onError(throwable)
                }
            }
            .launchIn(coroutineScope)
    }

    /**
     * Called for every emitted [MviIntent].
     * Must be implemented to define how each intent should be handled.
     */
    protected abstract suspend fun executeIntent(mviIntent: I)

    /**
     * Emits a new intent to be processed by [executeIntent].
     * Also logs the intents for debugging.
     */
    fun onIntent(mviIntent: I) {
        Kermit.tag(tag).i("Intent received: $mviIntent for $currentState")
        intentFlow.tryEmit(mviIntent)
    }

    /**
     * Applies state transformations using the provided [transform] function.
     * Emits the updated state and logs the changes.
     */
    protected fun updateUiState(transform: (oldState: S) -> S) {
        val newState = transform(uiState.value)
        Kermit.tag(tag).i("ViewState changed: from ${uiState.value} to $newState")
        _uiState.update { newState }
    }

    /**
     * Sends one-time side effects to the UI.
     * Also logs the effects for debugging.
     */
    protected suspend fun sendSideEffect(mviSideEffect: E) {
        Kermit.tag(tag).i("Side effect sent: $mviSideEffect")
        _sideEffect.emit(mviSideEffect)
    }

    /**
     * Clears the current side effect after it has been handled by the UI.
     * This is part of the one-time event pattern to prevent repeated emission.
     */
    fun onSideEffectHandled(handledSideEffect: E?) {
        if (handledSideEffect == sideEffect.value && handledSideEffect != null) {
            Kermit.tag(tag).i("Side effect handled: $handledSideEffect")
            _sideEffect.tryEmit(null)
        }
    }

    /**
     * Sends navigation effects to the UI.
     * Also logs the effects for debugging.
     */
    protected suspend fun sendNavEvent(mviNavEvent: N) {
        Kermit.tag(tag).i("Navigation event sent: $mviNavEvent")
        _navEvent.emit(mviNavEvent)
    }

    /**
     * Sends navigation events to the UI.
     * Also logs the events for debugging.
     */
    fun onNavEventHandled(handledNavEvent: N?) {
        if (handledNavEvent == navEvent.value && handledNavEvent != null) {
            Kermit.tag(tag).i("Navigation event handled: $handledNavEvent")
            _navEvent.tryEmit(null)
        }
    }

    /**
     * Launches a coroutine in the current [coroutineScope].
     * Can be overridden to change coroutine launching behavior (e.g., for testing).
     * @param block The suspending code block to execute.
     */
    protected open fun launchInScope(block: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(block = block)
    }

    /**
     * Clears the current navigation event after it has been handled by the UI.
     * This is part of the one-time event pattern to prevent repeated emission.
     */
    protected open suspend fun onError(throwable: Throwable) {
        Kermit.tag(tag).e("An error occurred: ${throwable.message}", throwable)
    }

    companion object {
        private const val DEFAULT_FLOW_BUFFER_CAPACITY = 64
        private const val DEFAULT_DEBOUNCE_TIMEOUT_MILLIS: Long = 500
    }
}
