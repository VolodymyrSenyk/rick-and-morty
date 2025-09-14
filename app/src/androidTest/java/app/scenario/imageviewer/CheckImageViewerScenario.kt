package app.scenario.imageviewer

import androidx.activity.ComponentActivity
import app.screen.imageviewer.ImageViewerScreen
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.findImage
import uitestutil.compose.scenario.ActivityComposeTestRule
import uitestutil.compose.scenario.BaseScenario
import uitestutil.compose.waitUntilDisplaying

class CheckImageViewerScenario<A : ComponentActivity>(
    private val contentDescription: String,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Image Viewer' content") {
                ImageViewerScreen(this).apply {
                    waitUntilDisplaying(findImage(contentDescription))
                    findImage(contentDescription).assertExists()
                    menuBack.assertExists()
                }
            }
        }
}
