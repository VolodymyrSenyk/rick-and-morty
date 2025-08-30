package navigation.compose.router

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.suspendCancellableCoroutine
import navigation.compose.Destination
import navigation.compose.NavArg
import navigation.compose.utils.name

/**
 * Jetpack Compose-specific implementation of [Router],
 * using [NavController] to manage navigation.
 *
 * @param navController The [NavController] instance used to perform navigation actions.
 */
class JetpackRouter(private val navController: NavController) : Router {

    override fun navigateTo(destination: Destination, singleTop: Boolean) {
        navController.navigate(destination) {
            launchSingleTop = singleTop
        }
    }

    override suspend fun <T : NavArg> navigateForResult(destination: Destination, argsKey: String, singleTop: Boolean): T? {
        navigateTo(destination, singleTop)
        return navController.previousBackStackEntry?.waitForResult(argsKey)
    }

    override fun backTo(destination: Destination, inclusive: Boolean, saveState: Boolean): Boolean? =
        navController.popBackStack(route = destination, inclusive = inclusive, saveState = saveState)

    override fun <T : NavArg> backWith(args: T): Boolean? {
        navController.previousBackStackEntry?.savedStateHandle?.set(args::class.name, args)
        return navController.popBackStack()
    }

    override fun back(): Boolean? = navController.popBackStack()

    private suspend fun <T : NavArg> NavBackStackEntry.waitForResult(navArgsKey: String): T? =
        suspendCancellableCoroutine { continuation ->
            val observer = object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_RESUME) {
                        val result = savedStateHandle.get<T?>(navArgsKey)
                        lifecycle.removeObserver(this)
                        savedStateHandle.remove<T?>(navArgsKey)
                        continuation.resume(result) { cause, _, _ -> cause.printStackTrace() }
                    }
                }
            }

            lifecycle.addObserver(observer)

            continuation.invokeOnCancellation {
                lifecycle.removeObserver(observer)
            }
        }
}
