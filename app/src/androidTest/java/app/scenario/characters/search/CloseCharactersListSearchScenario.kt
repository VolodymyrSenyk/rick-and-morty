package app.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.screen.characters.CharactersListSearchScreen

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
