package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.screen.characters.CharactersListScreen

class OpenCharactersListFilterScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Characters List Filter' from 'Characters List'") {
                CharactersListScreen(this).apply {
                    menuFilter.performClick()
                }
            }
        }
}
