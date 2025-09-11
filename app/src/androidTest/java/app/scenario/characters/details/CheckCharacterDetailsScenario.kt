package app.scenario.characters.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.screen.characters.CharacterDetailsScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.findText
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying
import uitestutil.compose.waitUntilHiding

class CheckCharacterDetailsScenario<A : ComponentActivity>(
    private val contentToCheck: List<String>,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Character Details' content and return back") {
                CharacterDetailsScreen(this).apply {
                    waitUntilHiding(progressBar)
                    contentToCheck.forEachIndexed { index, text ->
                        val ordinalNumber = contentToCheck.subList(0, index).count { it == text }
                        waitUntilDisplaying(findText(text, ordinalNumber))
                        findText(text, ordinalNumber).assertExists()
                    }
                    menuBack.performClick()
                }
            }
        }
}
