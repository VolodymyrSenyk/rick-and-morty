package arch.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arch.log.Kermit
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * A base [ViewModel] implementation that provides convenient coroutine helpers.
 *
 * This class simplifies launching coroutines within the [viewModelScope],
 * handles errors uniformly, and supports subscribing to [Flow]s with automatic error handling.
 *
 * @property coroutineContext The default coroutine context for [Flow] operations,
 * typically set to [Dispatchers.IO] for background work.
 */
abstract class BaseCoroutinesViewModel(
    protected val coroutineContext: CoroutineContext = Dispatchers.IO,
) : ViewModel() {

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
    fun setCoroutineScope(coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)) {
        this.coroutineScope = coroutineScope
    }

    /**
     * Launches a coroutine in [viewModelScope] with error handling.
     *
     * Catches all exceptions except [CancellationException] and delegates them to [onError].
     *
     * @param context The coroutine context to use, defaults to [EmptyCoroutineContext].
     * @param start The coroutine start option, defaults to [CoroutineStart.DEFAULT].
     * @param coroutine The suspending lambda to execute.
     */
    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        coroutine: suspend CoroutineScope.() -> Unit,
    ): Unit = coroutineScope.launch(context, start) {
        try {
            coroutine(this)
        } catch (exception: CancellationException) {
            throw exception
        } catch (throwable: Throwable) {
            onError(throwable)
        }
    }.toUnit()

    /**
     * Subscribes to this [Flow] with a handler for emitted values.
     *
     * Errors are caught and forwarded to [onError].
     *
     * @param onNext Lambda to handle each emitted value.
     */
    protected fun <T> Flow<T>.subscribe(
        onNext: suspend (value: T) -> Unit
    ): Unit = coroutineScope.launch {
        this@subscribe.subscribe(onNext) { throwable ->
            onError(throwable)
        }
    }.toUnit()

    /**
     * Subscribes to this [Flow] with handlers for emitted values and errors.
     *
     * Collection is performed on [coroutineContext].
     *
     * @param onNext Lambda to handle each emitted value.
     * @param onError Lambda to handle errors emitted by the flow.
     */
    protected fun <T> Flow<T>.subscribe(
        onNext: suspend (value: T) -> Unit,
        onError: suspend (value: Throwable) -> Unit
    ): Unit = coroutineScope.launch {
        this@subscribe
            .flowOn(this@BaseCoroutinesViewModel.coroutineContext)
            .catch {
                onError(it)
            }
            .collect {
                onNext(it)
            }
    }.toUnit()

    /**
     * Emits a new value to this [MutableStateFlow] in a coroutine launched in [viewModelScope].
     *
     * @param value The value to emit.
     */
    protected fun <T> MutableStateFlow<T>.emitWithScope(value: T) = launch {
        this@emitWithScope.emit(value)
    }

    /**
     * Called when an unhandled exception occurs inside launched coroutines or flows.
     *
     * Can be overridden to provide custom error handling or reporting.
     *
     * @param throwable The caught exception.
     */
    protected open fun onError(throwable: Throwable) {
        Kermit.e("An error occurred: ${throwable.message}", throwable)
    }

    private fun Any?.toUnit() = Unit
}
