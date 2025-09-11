package app.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.performTextReplacement
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findField
import app.core.utils.findListItemByIndex
import app.core.utils.findText
import app.core.utils.waitUntilDisplaying
import app.core.utils.waitUntilHiding
import app.screen.characters.CharactersListSearchScreen
import com.senyk.rickandmorty.feature.characters.ui.R

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
