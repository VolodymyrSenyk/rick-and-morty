package app.scenario.theme.check

import androidx.activity.ComponentActivity
import app.scenario.characters.details.OpenImageViewerScenario
import app.scenario.characters.list.OpenCharacterDetailsScenario
import app.screen.imageviewer.ImageViewerScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.scenario.PressDeviceBackButtonScenario

class CheckThemeOnImageViewerScenario<A : ComponentActivity>(
    private val characterName: String,
    private val isDayTheme: Boolean,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check theme on 'Image Viewer' screen") {
                scenario(OpenCharacterDetailsScenario(characterName))
                scenario(OpenImageViewerScenario(characterName))
                step("Check 'Image Viewer' screen theme") {
                    ImageViewerScreen(this).apply {
                        if (isDayTheme) {
                            menuDayTheme.assertExists()
                            menuNightTheme.assertDoesNotExist()
                        } else {
                            menuDayTheme.assertDoesNotExist()
                            menuNightTheme.assertExists()
                        }
                    }
                }
                scenario(PressDeviceBackButtonScenario())
                scenario(PressDeviceBackButtonScenario())
            }
        }
}
