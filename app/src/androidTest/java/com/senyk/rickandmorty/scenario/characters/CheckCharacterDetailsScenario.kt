package com.senyk.rickandmorty.scenario.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.screen.characters.CharacterDetailsScreen

class CheckCharacterDetailsScenario<A : ComponentActivity>(
    private val name: String,
    private val content: List<String>,
) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Check character details screen") {
                CharacterDetailsScreen(this).apply {
                    waitUntil(WAIT_DURATION_MEDIUM) { progressBar.isNotDisplayed() }
                    textTitle(name).assertExists()
                    content.forEachIndexed { index, text ->
                        val ordinalNumber = content.subList(0, index).count { it == text }
                        textListItem(ordinalNumber, text).assertExists()
                    }
                }
            }
        }
}
