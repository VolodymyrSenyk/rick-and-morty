package com.senyk.rickandmorty.core.base

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule

/**
 * Abstract base class for defining reusable test scenarios in Compose UI tests.
 *
 * @param A The activity type used in the test.
 */
abstract class BaseScenario<A : ComponentActivity> {

    /**
     * Compose test steps to execute in this scenario.
     */
    protected abstract val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit

    /**
     * Invokes the defined [steps] with the given [composeTestRule].
     *
     * @param composeTestRule The Compose test rule used to execute the scenario.
     */
    operator fun invoke(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>) {
        composeTestRule.waitForIdle()
        steps.invoke(composeTestRule)
    }

    /**
     * Executes the provided [scenario] within the current [AndroidComposeTestRule] context.
     */
    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.scenario(scenario: BaseScenario<A>) = scenario.invoke(this)

    companion object {
        const val WAIT_DURATION_LONG = 10_000L
        const val WAIT_DURATION_MEDIUM = 5_000L
    }
}
