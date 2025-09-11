package app.scenario.theme.change

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.scenario.characters.list.OpenCharacterDetailsScenario
import app.scenario.system.PressDeviceBackButtonScenario
import app.screen.characters.CharacterDetailsScreen

class ChangeThemeOnCharacterDetailsScenario<A : ComponentActivity>(
    private val characterName: String,
    private val isDayToNightTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check and change theme on 'Character details' screen") {
                scenario(OpenCharacterDetailsScenario(characterName))
                CharacterDetailsScreen(this).apply {
                    step("Check theme before change") {
                        if (isDayToNightTheme) {
                            menuDayTheme.assertExists()
                            menuNightTheme.assertDoesNotExist()
                        } else {
                            menuDayTheme.assertDoesNotExist()
                            menuNightTheme.assertExists()
                        }
                    }
                    step("Change theme") {
                        if (isDayToNightTheme) {
                            menuDayTheme.performClick()
                        } else {
                            menuNightTheme.performClick()
                        }
                    }
                    step("Check theme after change") {
                        if (!isDayToNightTheme) {
                            menuDayTheme.assertExists()
                            menuNightTheme.assertDoesNotExist()
                        } else {
                            menuDayTheme.assertDoesNotExist()
                            menuNightTheme.assertExists()
                        }
                    }
                }
                scenario(PressDeviceBackButtonScenario())
            }
        }
}
