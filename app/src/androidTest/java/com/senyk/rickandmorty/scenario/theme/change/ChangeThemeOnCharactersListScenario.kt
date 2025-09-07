package com.senyk.rickandmorty.scenario.theme.change

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.theme.check.CheckThemeOnCharactersListScenario
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

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
