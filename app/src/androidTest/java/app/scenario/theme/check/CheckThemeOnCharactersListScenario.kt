package app.scenario.theme.check

import androidx.activity.ComponentActivity
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.waitUntilDisplaying
import app.screen.characters.CharactersListScreen

class CheckThemeOnCharactersListScenario<A : ComponentActivity>(
    private val isDayTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check theme on 'Characters list' screen") {
                CharactersListScreen(this).apply {
                    waitUntilDisplaying(textTitle)
                    if (isDayTheme) {
                        menuDayTheme.assertExists()
                        menuNightTheme.assertDoesNotExist()
                    } else {
                        menuDayTheme.assertDoesNotExist()
                        menuNightTheme.assertExists()
                    }
                }
            }
        }
}
