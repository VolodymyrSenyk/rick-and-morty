package com.senyk.rickandmorty.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.screen.characters.CharactersListSearchScreen
import feature.characters.ui.R

class CheckCharactersListSearchClearingScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Characters List Search' search clearing") {
                CharactersListSearchScreen(this).apply {
                    menuClear.performClick()
                    findText(R.string.hint_search).assertExists()
                    textListEmptyState.assertDoesNotExist()
                }
            }
        }
}
