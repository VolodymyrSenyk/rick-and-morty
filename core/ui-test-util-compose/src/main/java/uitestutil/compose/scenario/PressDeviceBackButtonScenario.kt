package uitestutil.compose.scenario

import androidx.activity.ComponentActivity
import uitestutil.compose.StepsLogger.step
import uitestutil.compose.pressDeviceBackButton

class PressDeviceBackButtonScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Press device back button") {
                waitForIdle()
                pressDeviceBackButton()
            }
        }
}
