package com.senyk.rickandmorty.scenario.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.system.PressDeviceBackButtonScenario
import com.senyk.rickandmorty.screen.characters.CharacterDetailsScreen

class CheckCharacterFromListScenario<A : ComponentActivity>(
    private val name: String,
    private val content: List<String>,
    private val navigateBackWithSystemButton: Boolean = false,
) : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
        get() = {
            step("Open and check character details") {
                scenario(OpenCharacterDetailsScenario(name = name))
                scenario(CheckCharacterDetailsScenario(name = name, content = content))
                if (navigateBackWithSystemButton) {
                    scenario(PressDeviceBackButtonScenario())
                } else {
                    CharacterDetailsScreen(this).menuBack.performClick()
                }
            }
        }
}
