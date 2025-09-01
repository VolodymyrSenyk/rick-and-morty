package navigation.compose.router

import navigation.compose.Destination
import navigation.compose.NavArg
import navigation.compose.utils.collectResultAsStateWithLifecycle

/**
 * Defines a generic navigation interface for screen transitions.
 */
interface Router {

    /**
     * Navigates to the given [navigation.compose.Destination].
     *
     * @param destination The navigation target.
     * @param singleTop If true, avoids multiple copies of the destination on the back stack.
     */
    fun navigateTo(destination: Destination, singleTop: Boolean = true)

    /**
     * Navigates to the specified [destination] and suspends until a result of type [T] is received.
     *
     * **Note:** When the destination is registered via [androidx.navigation.compose.composable],
     * the coroutine will be canceled and result will be lost. In such cases,
     * consider using [collectResultAsStateWithLifecycle] to observe the result.
     *
     * Works reliably when the destination screen is registered via [androidx.navigation.compose.dialog],
     * as the coroutine remains active until the dialog returns a result.
     *
     * @param destination The navigation target.
     * @param argsKey The key used to identify the result in the saved state handle.
     * @param singleTop Whether to launch the destination as single top (default is true).
     * @return The navigation result of type [T], or null if no result was returned.
     */
    suspend fun <T : NavArg> navigateForResult(destination: Destination, argsKey: String, singleTop: Boolean): T?

    /**
     * Pops the back stack up to the given [Destination].
     *
     * @param destination The target route to return to.
     * @param inclusive Whether to also pop the [destination] itself.
     * @param saveState Whether to save the state of the popped destinations.
     * @return True if successful, false if the back stack wasn't changed.
     */
    fun backTo(destination: Destination, inclusive: Boolean = false, saveState: Boolean = false): Boolean?

    /**
     * Sets a navigation result and pops the back stack.
     *
     * @param args The result data to return to the previous destination.
     * @return True if successful, false if the back stack was empty.
     */
    fun <T : NavArg> backWith(args: T): Boolean?

    /**
     * Pops the last entry from the back stack.
     *
     * @return True if successful, false if the back stack is empty.
     */
    fun back(): Boolean?
}
