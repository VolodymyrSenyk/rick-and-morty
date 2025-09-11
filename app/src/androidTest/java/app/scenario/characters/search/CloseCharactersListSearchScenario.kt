package app.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.screen.characters.CharactersListSearchScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario

class CloseCharactersListSearchScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Close 'Characters List Search' with menu back button") {
                CharactersListSearchScreen(this).apply {
                    menuBack.performClick()
                }
            }
        }
}
