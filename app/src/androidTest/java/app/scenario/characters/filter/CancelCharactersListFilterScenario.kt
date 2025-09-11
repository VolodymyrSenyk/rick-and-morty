package app.scenario.characters.filter

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.findText
import app.screen.characters.CharactersListFilterScreen
import com.senyk.rickandmorty.feature.characters.ui.R

class CancelCharactersListFilterScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Apply 'Characters List Filter'") {
                CharactersListFilterScreen(this).apply {
                    textTitle.assertExists()
                    findText(R.string.status_all).assertExists()
                    findText(R.string.gender_all).assertExists()
                    buttonApply.assertExists()
                    buttonCancel.performClick()
                }
            }
        }
}
