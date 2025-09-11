package app.scenario.system

import androidx.activity.ComponentActivity
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.pressDeviceBackButton

class PressDeviceBackButtonScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Press device back button") {
                waitForIdle()
                pressDeviceBackButton()
            }
        }
}
