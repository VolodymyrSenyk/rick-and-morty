package app.scenario.theme.change

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.scenario.theme.check.CheckThemeOnCharactersListScenario
import app.screen.characters.CharactersListScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario

class ChangeThemeOnCharactersListScenario<A : ComponentActivity>(
    private val isDayToNightTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check and change theme on 'Characters list' screen") {
                CharactersListScreen(this).apply {
                    step("Check theme before change") {
                        scenario(CheckThemeOnCharactersListScenario(isDayTheme = isDayToNightTheme))
                    }
                    step("Change theme") {
                        if (isDayToNightTheme) {
                            menuDayTheme.performClick()
                        } else {
                            menuNightTheme.performClick()
                        }
                    }
                    step("Check theme after change") {
                        scenario(CheckThemeOnCharactersListScenario(isDayTheme = !isDayToNightTheme))
                    }
                }
            }
        }
}
