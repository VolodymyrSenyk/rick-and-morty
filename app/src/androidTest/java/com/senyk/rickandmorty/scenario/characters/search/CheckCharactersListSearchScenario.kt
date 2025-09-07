package com.senyk.rickandmorty.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.performTextReplacement
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findField
import com.senyk.rickandmorty.core.utils.findListItemByIndex
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.core.utils.waitUntilDisplaying
import com.senyk.rickandmorty.core.utils.waitUntilHiding
import com.senyk.rickandmorty.screen.characters.CharactersListSearchScreen
import feature.characters.ui.R

class CheckCharactersListSearchScenario<A : ComponentActivity>(
    private val searchQuery: String = "",
    private val characters: List<String> = emptyList(),
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Characters List Search' content") {
                CharactersListSearchScreen(this).apply {
                    waitUntilHiding(progressBar)
                    findText(R.string.hint_search).assertExists()
                    findText(R.string.hint_search).performTextReplacement(searchQuery)
                    if (searchQuery.isEmpty()) {
                        findText(R.string.hint_search).assertExists()
                        menuClear.assertDoesNotExist()
                        textListEmptyState.assertDoesNotExist()
                    } else {
                        findField(searchQuery).assertExists()
                        menuClear.assertExists()
                        if (characters.isEmpty()) {
                            textListEmptyState.assertExists()
                        } else {
                            waitUntilDisplaying(findText(characters.first()))
                            characters.forEachIndexed { index, name ->
                                findListItemByIndex(index, listIndex = 1).assertTextContains(name)
                            }
                        }
                    }
                }
            }
        }
}
