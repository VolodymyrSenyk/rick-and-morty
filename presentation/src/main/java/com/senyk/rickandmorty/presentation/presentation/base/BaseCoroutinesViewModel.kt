package com.senyk.rickandmorty.presentation.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseCoroutinesViewModel(
    protected val coroutineContext: CoroutineContext = Dispatchers.IO,
) : BaseViewModel() {

    protected fun launch(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        coroutine: suspend CoroutineScope.() -> Unit
    ): Job = viewModelScope.launch(context, start) {
        try {
            coroutine(this)
        } catch (exception: CancellationException) {
            throw exception
        } catch (throwable: Throwable) {
            onError(throwable)
        }
    }

    protected fun <T> Flow<T>.subscribe(
        onNext: suspend (value: T) -> Unit
    ): Job = viewModelScope.launch {
        this@subscribe.subscribe(onNext) { throwable ->
            onError(throwable)
        }
    }

    protected fun <T> Flow<T>.subscribe(
        onNext: suspend (value: T) -> Unit,
        onError: suspend (value: Throwable) -> Unit
    ): Job = viewModelScope.launch {
        this@subscribe
            .flowOn(this@BaseCoroutinesViewModel.coroutineContext)
            .catch {
                onError(it)
            }
            .collect {
                onNext(it)
            }
    }

    protected fun <T> MutableStateFlow<T>.emitWithScope(value: T) = launch {
        this@emitWithScope.emit(value)
    }
}
