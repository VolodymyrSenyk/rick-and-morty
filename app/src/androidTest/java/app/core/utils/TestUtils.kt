package app.core.utils

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice

/**
 * Returns the target context of the test application.
 */
fun getTargetContext(): Context = InstrumentationRegistry.getInstrumentation().targetContext

/**
 * Returns the [UiDevice] instance for interacting with the device.
 */
fun getUiDevice(): UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

/**
 * Presses the device back button.
 *
 * @return True if the button was successfully pressed.
 */
fun pressDeviceBackButton(): Boolean = getUiDevice().pressBack()
