package uitestutil.compose

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasAnySibling
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasParent
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isEditable
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import uitestutil.compose.scenario.ActivityComposeTestRule

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
 * Finds an item by [index] inside a scrollable container identified by [listIndex].
 *
 * @param index The index of the item inside the target scrollable container.
 * @param listIndex The index of the scrollable container among all containers on screen (default is 0).
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findListItemByIndex(
    index: Int,
    listIndex: Int = 0,
): SemanticsNodeInteraction = onAllNodes(hasScrollAction(), useUnmergedTree = false)[listIndex].onChildren()[index]

/**
 * Finds an item by [text] inside a scrollable container identified by [listIndex].
 *
 * @param text The exact text to match.
 * @param listIndex The index of the scrollable container among all containers on screen (default is 0).
 */
fun <A : ComponentActivity> ActivityComposeTestRule<A>.findListItemByText(
    text: String,
    listIndex: Int = 0,
): SemanticsNodeInteraction =
    onAllNodes(hasScrollAction(), useUnmergedTree = false)[listIndex]
        .onChildren()
        .filterToOne(hasAnyChild(hasText(text)))

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
