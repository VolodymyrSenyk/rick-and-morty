package app.scenario.characters.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import app.screen.characters.CharacterDetailsScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying

class OpenImageViewerScenario<A : ComponentActivity>(
    private val characterName: String,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Open 'Image Viewer' from 'Character Details' for $characterName") {
                CharacterDetailsScreen(this).apply {
                    waitUntilDisplaying(characterImage)
                    characterImage.performClick()
                }
            }
        }
}
