package navigation.compose.utils

import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import arch.log.Kermit

/**
 * Returns a Hilt-injected [ViewModel] scoped to the current [ComponentActivity].
 *
 * This is useful when you are in a nested Composable (e.g., inside a NavHost, Dialog, or BottomSheet)
 * and need access to an Activity-scoped ViewModel rather than the one scoped to the navigation graph or fragment.
 *
 * If the current context is not a [ComponentActivity], it falls back to the default `hiltViewModel<VM>()`,
 * which may lead to a different scoping behavior.
 *
 * @return [ViewModel] instance scoped to the current [ComponentActivity] if possible, otherwise default-scoped.
 */
@Composable
inline fun <reified VM : ViewModel> hiltActivityViewModel(): VM {
    val activity = currentActivity()
    return if (activity != null) {
        hiltViewModel<VM>(viewModelStoreOwner = activity)
    } else {
        Kermit.w("Fallback to default ViewModel scope for ${VM::class.java.simpleName}. Activity not found in current context.")
        hiltViewModel<VM>()
    }
}

/**
 * Attempts to retrieve the current [ComponentActivity] from the [LocalContext].
 *
 * This function unwraps the context chain recursively until it finds an instance
 * of [ComponentActivity]. If none is found, it returns `null`.
 *
 * @return The current [ComponentActivity] if available; otherwise, `null`.
 */
@Composable
fun currentActivity(): ComponentActivity? {
    var context = LocalContext.current
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    Kermit.w("ComponentActivity not found in current context chain.")
    return null
}
