package app.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findText
import app.screen.characters.CharactersListSearchScreen
import com.senyk.rickandmorty.feature.characters.ui.R

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
