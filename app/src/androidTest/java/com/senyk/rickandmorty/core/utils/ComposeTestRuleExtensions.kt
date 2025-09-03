package com.senyk.rickandmorty.core.utils

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.isDialog
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
 * @throws AssertionError if the node is still visible after the timeout (5 seconds).
 */
fun ComposeTestRule.waitUntilHiding(node: SemanticsNodeInteraction) {
    waitUntil(5_000L) {
        node.isNotDisplayed()
    }
}
