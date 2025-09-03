package com.senyk.rickandmorty.scenario.system

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isDisplayed
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class WaitUntilStartScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Wait until first screen will be ready for testing") {
                CharactersListScreen(this).apply {
                    waitUntil(10_000L) {
                        textTitle.isDisplayed()
                    }
                }
            }
        }
}
