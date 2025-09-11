package uitestutil.compose

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.isRoot
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onFirst

/**
 * Returns the root node of the current screen.
 */
fun ComposeTestRule.rootScreen(): SemanticsNodeInteraction =
    onAllNodes(isRoot(), useUnmergedTree = true).onFirst()

/**
 * Returns the first dialog node on the screen, or null if none is found.
 */
fun ComposeTestRule.dialog(): SemanticsNodeInteraction? {
    val dialogNodes = onAllNodes(isDialog() and hasAnyChild(hasParent(isDialog())), useUnmergedTree = true)
    return dialogNodes.fetchSemanticsNodes(atLeastOneRootRequired = false).firstOrNull()?.let {
        dialogNodes.onFirst()
    }
}

/**
 * Returns the topmost semantics node, preferring a dialog if present, otherwise the root screen.
 */
fun ComposeTestRule.currentSemanticsTree(): SemanticsNodeInteraction = dialog() ?: rootScreen()

/**
 * Waits until the given [node] is no longer displayed on the screen.
 *
 * @param node The node to wait for hiding.
 * @throws AssertionError if the node is still visible after the timeout.
 */
fun ComposeTestRule.waitUntilHiding(node: SemanticsNodeInteraction) {
    waitUntil(WAIT_TIME_MILLIS) {
        node.isNotDisplayed()
    }
}

/**
 * Waits until the given [node] is displayed on the screen.
 *
 * @param node The node to wait for displaying.
 * @throws AssertionError if the node is still not visible after the timeout.
 */
fun ComposeTestRule.waitUntilDisplaying(node: SemanticsNodeInteraction) {
    waitUntil(WAIT_TIME_MILLIS) {
        node.isDisplayed()
    }
}

/**
 * Timeout in milliseconds used for waiting in UI tests.
 *
 * A large value is chosen to ensure stability in CI environments,
 * where tests may run slower. For local test runs, this value can
 * be reduced to speed up execution.
 */
private val WAIT_TIME_MILLIS = if (System.getenv("CI") == "true") 60_000L else 5_000L
