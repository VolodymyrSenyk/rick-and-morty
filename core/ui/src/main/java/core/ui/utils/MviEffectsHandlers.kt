package core.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import arch.android.BaseSimpleMviViewModel
import arch.mvi.MviIntent
import arch.mvi.MviNavEvent
import arch.mvi.MviSideEffect
import arch.mvi.ViewState

@Composable
fun <S : ViewState, I : MviIntent, E : MviSideEffect, N : MviNavEvent> SideEffectHandler(
    viewModel: BaseSimpleMviViewModel<S, I, E, N>,
    content: suspend (sideEffect: E) -> Unit,
) {
    val sideEffect = viewModel.sideEffect.collectAsStateWithLifecycle().value
    HandleEvent(sideEffect, viewModel::onSideEffectHandled, content)
}

@Composable
fun <S : ViewState, I : MviIntent, E : MviSideEffect, N : MviNavEvent> NavEventHandler(
    viewModel: BaseSimpleMviViewModel<S, I, E, N>,
    content: suspend (navEvent: N) -> Unit,
) {
    val navEvent = viewModel.navEvent.collectAsStateWithLifecycle().value
    HandleEvent(navEvent, viewModel::onNavEventHandled, content)
}

@Composable
fun <T> HandleEvent(
    event: T?,
    onEventHandled: (T?) -> Unit,
    content: suspend (T) -> Unit,
) {
    LaunchedEffect(event) {
        event?.let { content(it) }
        onEventHandled(event)
    }
}
