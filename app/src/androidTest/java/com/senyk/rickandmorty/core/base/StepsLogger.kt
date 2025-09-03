package com.senyk.rickandmorty.core.base

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.printToLog
import arch.log.Kermit
import com.senyk.rickandmorty.core.utils.currentSemanticsTree
import java.util.Stack

/**
 * A utility for hierarchical logging of UI test steps with automatic numbering and nesting support.
 *
 * Each `step(...)` call:
 * - Increments the current level's counter,
 * - Logs the start and end (or error) of the step,
 * - Supports unlimited nesting of steps,
 * - Automatically resets deeper-level counters on step completion.
 *
 * Example output:
 * ```
 * 1: Launch app | STARTED
 * 1.1: Click login button | STARTED
 * 1.1: Click login button | FINISHED
 * 1: Launch app | FINISHED
 * ```
 */
object StepsLogger {

    const val TAG = "UiTesting"

    private val counters = mutableListOf(0) // Initial root level counter
    private val stepsStack = Stack<String>()

    private var composeTestRule: ComposeTestRule? = null

    private var hasLoggedError = false

    /**
     * Initializes the logger with a Compose test rule, required for printing the semantics tree.
     */
    fun setComposeTestRule(rule: ComposeTestRule) {
        composeTestRule = rule
        hasLoggedError = false
    }

    /**
     * Logs a test step with structured numbering and nested formatting.
     *
     * @param text A description of the step to log.
     * @param actions A lambda block representing the test step actions.
     */
    fun step(text: String, actions: () -> Unit) {
        val depth = stepsStack.size

        // Ensure counters has enough levels
        if (depth >= counters.size) {
            counters.add(0)
        }

        counters[depth]++ // Increment counter at this depth

        // Build step number (e.g., 1.2.3)
        val stepNumber = counters.take(depth + 1).joinToString(".")

        stepsStack.push(text)
        Kermit.tag(TAG).i("$stepNumber: $text | STARTED")

        try {
            actions()
            Kermit.tag(TAG).i("$stepNumber: $text | FINISHED")

            // Reset all deeper counters
            for (i in depth + 1 until counters.size) {
                counters[i] = 0
            }
        } catch (error: Throwable) {
            if (!hasLoggedError) {
                Kermit.tag(TAG).e("$stepNumber: $text | ERROR", error)
                composeTestRule?.let { testRule ->
                    try {
                        testRule.currentSemanticsTree().printToLog("UiTesting")
                    } catch (treeError: Throwable) {
                        Kermit.tag(TAG).e("Failed to dump semantics tree", treeError)
                    }
                }
                hasLoggedError = true
            }
            counters.replaceAll { 0 } // Reset everything on failure
            throw error
        } finally {
            stepsStack.pop()
        }
    }
}
