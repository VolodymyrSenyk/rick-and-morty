package com.senyk.rickandmorty.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListSearchScreen

class CloseCharactersListSearchScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Close 'Characters List Search' with menu back button") {
                CharactersListSearchScreen(this).apply {
                    menuBack.performClick()
                }
            }
        }
}
