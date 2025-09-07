package com.senyk.rickandmorty.testcase

import com.senyk.rickandmorty.core.base.BaseTestCase
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.system.ReopenAppScenario
import com.senyk.rickandmorty.scenario.system.WaitUntilStartScenario
import com.senyk.rickandmorty.scenario.theme.change.ChangeThemeOnCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.theme.change.ChangeThemeOnCharactersListScenario
import com.senyk.rickandmorty.scenario.theme.check.CheckThemeOnCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.theme.check.CheckThemeOnCharactersListScenario
import org.junit.Test

class ThemeChangingFeatureTestCase : BaseTestCase() {

    @Test
    fun theme() {
        scenario(WaitUntilStartScenario())
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
