package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performClick
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.findListItemByText
import uitestutil.compose.findText
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying
import uitestutil.compose.waitUntilHiding

class OpenCharacterDetailsScenario<A : ComponentActivity>(
    private val characterName: String,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Character Details' from 'Characters List' for $characterName") {
                CharactersListScreen(this).apply {
                    waitUntilDisplaying(findText(characterName))
                    waitUntilHiding(findListItemByText(characterName).onChildren()[1])
                    findText(characterName).performClick()
                }
            }
        }
}
