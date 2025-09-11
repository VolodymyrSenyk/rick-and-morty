package app.scenario.theme.check

import androidx.activity.ComponentActivity
import app.scenario.characters.list.OpenCharacterDetailsScenario
import app.screen.characters.CharacterDetailsScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.scenario.PressDeviceBackButtonScenario

class CheckThemeOnCharacterDetailsScenario<A : ComponentActivity>(
    private val characterName: String,
    private val isDayTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check theme on 'Characters details' screen") {
                scenario(OpenCharacterDetailsScenario(characterName))
                step("Check 'Characters details' screen theme") {
                    CharacterDetailsScreen(this).apply {
                        if (isDayTheme) {
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
