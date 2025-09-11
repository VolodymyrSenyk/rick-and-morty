package app.scenario.characters.list

import androidx.activity.ComponentActivity
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying

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
