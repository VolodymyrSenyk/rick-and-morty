package app.testcase

import app.scenario.theme.change.ChangeThemeOnCharacterDetailsScenario
import app.scenario.theme.change.ChangeThemeOnCharactersListScenario
import app.scenario.theme.check.CheckThemeOnCharacterDetailsScenario
import app.scenario.theme.check.CheckThemeOnCharactersListScenario
import org.junit.Test
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ReopenAppScenario

class ThemeChangingFeatureTestCase : BaseTestCase() {

    @Test
    fun theme() {
        step("Change app theme") {
            scenario(ChangeThemeOnCharactersListScenario(isDayToNightTheme = true))
        }
        step("Check app theme on relaunch") {
            scenario(ReopenAppScenario())
            scenario(CheckThemeOnCharactersListScenario(isDayTheme = false))
            scenario(CheckThemeOnCharacterDetailsScenario(characterName = "Rick Sanchez", isDayTheme = false))
        }
        step("Change app theme one more time") {
            scenario(ChangeThemeOnCharactersListScenario(isDayToNightTheme = false))
        }
        step("Check app theme on relaunch one more time") {
            scenario(ReopenAppScenario())
            scenario(CheckThemeOnCharactersListScenario(isDayTheme = true))
            scenario(CheckThemeOnCharacterDetailsScenario(characterName = "Rick Sanchez", isDayTheme = true))
        }
        step("Change app theme on other screen") {
            scenario(ChangeThemeOnCharacterDetailsScenario(characterName = "Rick Sanchez", isDayToNightTheme = true))
        }
        step("Check app theme on relaunch after it changing on other screen") {
            scenario(ReopenAppScenario())
            scenario(CheckThemeOnCharactersListScenario(isDayTheme = false))
            scenario(CheckThemeOnCharacterDetailsScenario(characterName = "Rick Sanchez", isDayTheme = false))
        }
    }
}
