package com.senyk.rickandmorty.core.utils

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.onAllNodesWithText
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule

/**
 * Returns a localized string from the activity under test.
 *
 * @param resId The string resource id.
 * @param args  Optional string format arguments.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.getString(
    @StringRes resId: Int,
    vararg args: String,
): String = activity.getString(resId, *args)

/**
 * Finds a node with the given text.
 *
 * @param text The exact text to match.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findText(
    text: String,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction = onAllNodesWithText(text, useUnmergedTree)[index]

/**
 * Finds a node with the given string resource.
 *
 * @param textRes The string resource id.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 * @param args  Optional string format arguments.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findText(
    @StringRes textRes: Int,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
    vararg args: String,
): SemanticsNodeInteraction = findText(getString(resId = textRes, *args), index, useUnmergedTree)

/**
 * Finds a clickable text button with the given text.
 *
 * @param text The exact text to match.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findTextButton(
    text: String,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction = onAllNodes(hasText(text) and hasParent(hasClickAction()), useUnmergedTree)[index]

/**
 * Finds a clickable text button with the given string resource.
 *
 * @param textRes The string resource id.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findTextButton(
    @StringRes textRes: Int,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction = findTextButton(getString(textRes), index, useUnmergedTree)

/**
 * Finds an icon button by its content description string resource.
 *
 * @param textRes The string resource id.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findIconButton(
    @StringRes textRes: Int,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction =
    onAllNodes(hasContentDescription(getString(textRes)) and hasParent(hasClickAction()), useUnmergedTree)[index]

/**
 * Finds an editable field with the given text.
 *
 * @param text The exact text to match.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findField(
    text: String,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction = onAllNodes(isEditable() and hasText(text), useUnmergedTree)[index]

/**
 * Finds an editable field by its hint text string resource.
 *
 * @param hintRes The hint text string resource id.
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findFieldByHint(
    @StringRes hintRes: Int,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction =
    onAllNodes(isEditable() and hasAnySibling(hasText(getString(hintRes))), useUnmergedTree)[index]

/**
 * Finds a list item by [index] within a scrollable container.
 *
 * @param index The index of the matched scrollable container node.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findListItemByIndex(
    index: Int,
): SemanticsNodeInteraction = onAllNodes(hasAnyAncestor(hasScrollAction()), useUnmergedTree = false)[index]

/**
 * Finds a progress bar with the given [rangeInfo].
 *
 * @param rangeInfo The expected [ProgressBarRangeInfo], defaults to [ProgressBarRangeInfo.Indeterminate].
 * @param index The index of the matched node (if multiple exist).
 * @param useUnmergedTree Whether to include merged semantics.
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findProgressBar(
    rangeInfo: ProgressBarRangeInfo = ProgressBarRangeInfo.Indeterminate,
    index: Int = 0,
    useUnmergedTree: Boolean = true,
): SemanticsNodeInteraction = onAllNodes(hasProgressBarRangeInfo(rangeInfo), useUnmergedTree)[index]
