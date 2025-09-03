package com.senyk.rickandmorty.scenario.system

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.pressDeviceBackButton

class PressDeviceBackButtonScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Press device back button") {
                waitForIdle()
                pressDeviceBackButton()
            }
        }
}
