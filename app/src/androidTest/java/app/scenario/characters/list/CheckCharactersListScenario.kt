package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onChildren
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findListItemByIndex
import app.core.utils.waitUntilDisplaying
import app.core.utils.waitUntilHiding
import app.screen.characters.CharactersListScreen

class CheckCharactersListScenario<A : ComponentActivity>(
    private val characters: List<String>,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Characters List' content") {
                CharactersListScreen(this).apply {
                    waitUntilHiding(progressBar)
                    textTitle.assertExists()
                    if (characters.isEmpty()) {
                        textListEmptyState.assertExists()
                    } else {
                        waitUntilDisplaying(findListItemByIndex(0))
                        characters.forEachIndexed { index, name ->
                            findListItemByIndex(index).onChildren()[0].assertTextEquals(name)
                        }
                    }
                }
            }
        }
}
