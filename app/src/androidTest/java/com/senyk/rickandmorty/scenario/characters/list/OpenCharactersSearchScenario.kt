package com.senyk.rickandmorty.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class OpenCharactersSearchScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Characters List Search' from 'Characters List'") {
                CharactersListScreen(this).apply {
                    menuSearch.performClick()
                }
            }
        }
}
