package com.senyk.rickandmorty.core.util

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.isDialog
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
