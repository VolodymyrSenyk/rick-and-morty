package app.scenario.characters.list

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.scenario.characters.details.OpenImageViewerScenario
import app.scenario.imageviewer.CheckImageViewerScenario
import app.screen.characters.CharacterDetailsScreen
import app.screen.imageviewer.ImageViewerScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario

class CheckImageViewerFromListScenario<A : ComponentActivity>(
    private val characterName: String,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Character Details' content and return back") {
                scenario(OpenCharacterDetailsScenario(characterName))
                scenario(OpenImageViewerScenario(characterName))
                scenario(CheckImageViewerScenario(characterName))
                step("Return back") {
                    ImageViewerScreen(this).apply {
                        menuBack.performClick()
                    }
                    CharacterDetailsScreen(this).apply {
                        menuBack.performClick()
                    }
                }
            }
        }
}
