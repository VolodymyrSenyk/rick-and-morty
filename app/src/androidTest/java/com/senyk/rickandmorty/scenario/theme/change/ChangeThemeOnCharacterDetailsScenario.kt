package com.senyk.rickandmorty.scenario.theme.change

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.characters.OpenCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.system.PressDeviceBackButtonScenario
import com.senyk.rickandmorty.scenario.theme.check.CheckThemeOnCharactersListScenario
import com.senyk.rickandmorty.screen.characters.CharacterDetailsScreen

class ChangeThemeOnCharacterDetailsScenario<A : ComponentActivity>(
    private val characterName: String,
    private val isDayToNightTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Check and change theme on 'Character details' screen") {
                scenario(OpenCharacterDetailsScenario(characterName))
                CharacterDetailsScreen(this).apply {
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
                scenario(PressDeviceBackButtonScenario())
            }
        }
}
