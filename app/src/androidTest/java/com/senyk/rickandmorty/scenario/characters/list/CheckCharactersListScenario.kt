package com.senyk.rickandmorty.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextEquals
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findListItemByIndex
import com.senyk.rickandmorty.core.utils.waitUntilHiding
import com.senyk.rickandmorty.screen.characters.CharactersListScreen

class CheckCharactersListScenario<A : ComponentActivity>(
    private val characters: List<String>,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Characters List' content") {
                CharactersListScreen(this).apply {
                    waitUntilHiding(progressBar)
                    textTitle.assertExists()
                    if (characters.isEmpty()) {
                        textListEmptyState.assertExists()
                    } else {
                        characters.forEachIndexed { index, name ->
                            findListItemByIndex(index).assertTextEquals(name)
                        }
                    }
                }
            }
        }
}
