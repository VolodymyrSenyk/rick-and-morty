package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying

class OpenCharactersSearchScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Characters List Search' from 'Characters List'") {
                CharactersListScreen(this).apply {
                    waitUntilDisplaying(menuSearch)
                    menuSearch.performClick()
                }
            }
        }
}
