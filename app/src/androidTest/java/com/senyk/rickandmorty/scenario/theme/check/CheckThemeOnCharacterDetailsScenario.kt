package com.senyk.rickandmorty.scenario.theme.check

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.characters.list.OpenCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.system.PressDeviceBackButtonScenario
import com.senyk.rickandmorty.screen.characters.CharacterDetailsScreen

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
