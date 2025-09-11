package app.scenario.characters.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findText
import app.core.utils.waitUntilDisplaying
import app.core.utils.waitUntilHiding
import app.screen.characters.CharacterDetailsScreen

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
