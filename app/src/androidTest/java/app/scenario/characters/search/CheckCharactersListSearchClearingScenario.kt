package app.scenario.characters.search

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.screen.characters.CharactersListSearchScreen
import com.senyk.rickandmorty.feature.characters.ui.R
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.findText
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario

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
