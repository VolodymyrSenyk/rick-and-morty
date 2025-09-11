package app.scenario.theme.check

import androidx.activity.ComponentActivity
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying

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
