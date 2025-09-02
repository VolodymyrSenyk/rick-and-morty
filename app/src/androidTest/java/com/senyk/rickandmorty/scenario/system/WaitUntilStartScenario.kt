package com.senyk.rickandmorty.scenario.system

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class WaitUntilStartScenario<A : ComponentActivity>() : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Wait until first screen will be ready for testing") {
                CharactersListScreen(this).apply {
                    waitUntil(WAIT_DURATION_LONG) {
                        textTitle.isDisplayed()
                    }
                }
            }
        }
}
