package com.senyk.rickandmorty.scenario.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class OpenCharacterDetailsScenario<A : ComponentActivity>(private val name: String) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Open character details screen from characters list") {
                CharactersListScreen(this).apply {
                    textListItem(name).performClick()
                }
            }
        }
}
