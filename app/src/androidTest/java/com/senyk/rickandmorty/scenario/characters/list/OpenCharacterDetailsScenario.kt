package com.senyk.rickandmorty.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.core.utils.waitUntilDisplaying
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class OpenCharacterDetailsScenario<A : ComponentActivity>(
    private val characterName: String,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Character Details' from 'Characters List' for $characterName") {
                CharactersListScreen(this).apply {
                    waitUntilDisplaying(findText(characterName))
                    findText(characterName).performClick()
                }
            }
        }
}
