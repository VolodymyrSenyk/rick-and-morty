package app.testcase

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import app.MainActivity
import org.junit.Before
import org.junit.Rule
import uitestutil.compose.StepsLogger
import uitestutil.compose.scenario.BaseScenario

/**
 * Base class for Compose UI tests that provides rule setup and scenario support.
 */
abstract class BaseTestCase {

    /**
     * Compose test rule for launching [MainActivity] and executing UI tests.
     */
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * Initializes logging and other common test setup before each test runs.
     */
    @Before
    fun setup() {
        StepsLogger.setComposeTestRule(composeTestRule)
    }

    /**
     * Runs the specified [scenario] using the Compose test rule.
     *
     * @param scenario The UI test scenario to execute.
     */
    fun scenario(scenario: BaseScenario<MainActivity>) = scenario.invoke(composeTestRule)
}
