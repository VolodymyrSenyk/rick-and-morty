package com.senyk.rickandmorty.scenario.theme.check

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.waitUntilDisplaying
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

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
