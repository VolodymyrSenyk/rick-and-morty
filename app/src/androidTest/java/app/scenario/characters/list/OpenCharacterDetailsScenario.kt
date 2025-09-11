package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findListItemByText
import app.core.utils.findText
import app.core.utils.waitUntilDisplaying
import app.core.utils.waitUntilHiding
import app.screen.characters.CharactersListScreen

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
