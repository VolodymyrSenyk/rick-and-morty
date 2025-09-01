package navigation.compose.router

import navigation.compose.Destination
import navigation.compose.NavArg

/**
 * Stub implementation of the [Router] interface, used primarily for design-time previews in Compose.
 *
 * This class provides no-op (no operation) implementations for all navigation methods.
 * It allows composables that depend on [Router] to be previewed without requiring a real navigation environment.
 *
 * Typical usage:
 * ```
 * @Preview
 * @Composable
 * private fun MyDialogPreview() {
 *     MyDialog(router = RouterStub())
 * }
 * ```
 */
class RouterStub : Router {

    override fun navigateTo(destination: Destination, singleTop: Boolean) {
        // Stub!
    }

    override suspend fun <T : NavArg> navigateForResult(destination: Destination, argsKey: String, singleTop: Boolean): T? {
        return null
    }

    override fun backTo(destination: Destination, inclusive: Boolean, saveState: Boolean): Boolean? = null

    override fun <T : NavArg> backWith(args: T): Boolean? = null

    override fun back(): Boolean? = null
}
