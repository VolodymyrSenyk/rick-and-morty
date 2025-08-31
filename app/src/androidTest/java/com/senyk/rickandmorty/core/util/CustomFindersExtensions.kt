package com.senyk.rickandmorty.core.util

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * Returns a localized string from the activity using the provided [resId] and optional [args].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.getString(
    @StringRes resId: Int,
    vararg args: String,
): String = this.activity.getString(resId, *args)

/**
 * Finds a node with the exact [text].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findText(
    text: String,
): SemanticsNodeInteraction = onNode(hasTextExactly(text), useUnmergedTree = true)

/**
 * Finds a node with the text from the given string resource [textRes] and optional [args].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findText(
    @StringRes textRes: Int,
    vararg args: String,
): SemanticsNodeInteraction = findText(getString(textRes, *args))

/**
 * Finds a text button by its string resource [textRes].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findTextButton(
    @StringRes textRes: Int,
): SemanticsNodeInteraction = findTextButton(getString(textRes))

/**
 * Finds a text button with the given [text] and a clickable parent.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findTextButton(
    text: String,
): SemanticsNodeInteraction = onNode(hasTextExactly(text) and hasParent(hasClickAction()), useUnmergedTree = true)

/**
 * Finds an icon button by its content description string resource [textRes].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findIconButton(
    @StringRes textRes: Int,
): SemanticsNodeInteraction = onNode(
    hasContentDescriptionExactly(getString(textRes)) and hasParent(hasClickAction()),
    useUnmergedTree = true
)

/**
 * Finds a text field that has a sibling node with a hint matching [hintRes].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findFieldWithHint(
    @StringRes hintRes: Int,
): SemanticsNodeInteraction = onNode(
    isEditable() and hasAnySibling(hasTextExactly(getString(hintRes))),
    useUnmergedTree = true
)

/**
 * Finds a text field with the exact [text].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findField(
    text: String,
): SemanticsNodeInteraction = onNode(isEditable() and hasTextExactly(text), useUnmergedTree = true)

/**
 * Finds node with the exact [text] and position in scrollable list.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findListItemWithIndex(
    index: Int,
    text: String,
): SemanticsNodeInteraction = onAllNodes(hasParent(hasScrollAction()), useUnmergedTree = false)[index].assertTextEquals(text)

/**
 * Finds all node with the exact [text] and returns by [duplicateNodeIndex].
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.findListItem(
    duplicateNodeIndex: Int,
    text: String,
): SemanticsNodeInteraction = onAllNodesWithText(text, useUnmergedTree = true)[duplicateNodeIndex]
