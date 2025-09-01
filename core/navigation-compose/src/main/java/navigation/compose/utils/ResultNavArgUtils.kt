package navigation.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import navigation.compose.Destination
import navigation.compose.NavArg
import navigation.compose.router.Router
import kotlin.reflect.KClass

/**
 * Collects a navigation result of type [T] from [SavedStateHandle] once and passes it to [onResult],
 * clearing the result immediately after to prevent repeated delivery.
 *
 * Useful for one-time navigation results such as dialog responses.
 *
 * @receiver The [NavBackStackEntry] that holds the [SavedStateHandle] with the result.
 * @param onResult Lambda that receives the result when it's available.
 */
@Composable
inline fun <reified T : NavArg> NavBackStackEntry.collectResultAsStateWithLifecycle(crossinline onResult: (T) -> Unit) {
    val result = savedStateHandle.getMutableStateFlow<T?>(T::class.name, null).collectAsStateWithLifecycle(null).value
    LaunchedEffect(result) {
        result?.let {
            onResult(it)
            savedStateHandle[T::class.name] = null
        }
    }
}

/**
 * Navigates to the specified [destination] and suspends until a result of type [T] is received.
 *
 * **Note:** When the destination is registered via [androidx.navigation.compose.composable],
 * the coroutine will be canceled and result will be lost. In such cases,
 * consider using [collectResultAsStateWithLifecycle] or SharedViewModel pattern.
 *
 * Works reliably when the destination screen is registered via [androidx.navigation.compose.dialog],
 * as the coroutine remains active until the dialog returns a result.
 *
 * This is an inline reified convenience extension that uses the fully qualified class name of [T]
 * as the key to identify the navigation result in the saved state handle.
 *
 * @param destination The navigation target.
 * @param singleTop Whether to launch the destination as single top (default is true).
 * @return The navigation result of type [T], or null if no result was returned.
 */
suspend inline fun <reified T : NavArg> Router.navigateForResult(
    destination: Destination,
    singleTop: Boolean = true,
): T? = navigateForResult(destination, T::class.name, singleTop)

/**
 * Returns a unique, stable string identifier for this [KClass], prioritizing
 * the fully qualified class name, falling back to the simple name or Java simple name if needed.
 *
 * @receiver The [KClass] whose name should be resolved.
 * @return A unique string identifier for the class.
 */
val KClass<*>.name: String get() = qualifiedName ?: simpleName ?: this.java.simpleName
