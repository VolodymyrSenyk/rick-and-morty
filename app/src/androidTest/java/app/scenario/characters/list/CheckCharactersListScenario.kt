package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.onChildren
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.findListItemByIndex
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying
import uitestutil.compose.waitUntilHiding

class CheckCharactersListScenario<A : ComponentActivity>(
    private val characters: List<String>,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Characters List' content") {
                CharactersListScreen(this).apply {
                    waitUntilHiding(progressBar)
                    waitUntilDisplaying(textTitle)
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
