package app.scenario.system

import androidx.activity.ComponentActivity
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.waitUntilDisplaying
import app.screen.characters.CharactersListScreen

class WaitUntilStartScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Wait until first screen will be ready for testing") {
                CharactersListScreen(this).apply {
                    waitUntilDisplaying(textTitle)
                }
            }
        }
}
