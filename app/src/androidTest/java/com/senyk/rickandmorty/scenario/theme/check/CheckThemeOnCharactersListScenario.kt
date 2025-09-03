package com.senyk.rickandmorty.scenario.theme.check

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class CheckThemeOnCharactersListScenario<A : ComponentActivity>(
    private val isDayTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Check theme on 'Characters list' screen") {
                CharactersListScreen(this).apply {
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
