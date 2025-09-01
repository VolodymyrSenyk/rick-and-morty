package com.senyk.rickandmorty.scenario.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class CheckCharactersListScenario<A : ComponentActivity>(private val characters: List<String>) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Check characters list content") {
                CharactersListScreen(this).apply {
                    waitUntil(5000L) { progressBar.isNotDisplayed() }
                    textTitle.assertExists()
                    if (characters.isEmpty()) {
                        textListEmptyState.assertExists()
                    } else {
                        characters.forEachIndexed { index, name ->
                            textListItem(index, name).assertExists()
                        }
                    }
                }
            }
        }
}
