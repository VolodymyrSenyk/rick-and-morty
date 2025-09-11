package app.scenario.system

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import app.core.base.ActivityComposeTestRule
import app.core.base.BaseScenario
import app.core.base.StepsLogger.step
import app.core.utils.getTargetContext
import app.core.utils.getUiDevice

class ReopenAppScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Reopen application") {
                val device = getUiDevice()
                val context = getTargetContext()
                val packageName = context.packageName
                step("Close application") {
                    device.pressHome()
                    device.wait(Until.hasObject(By.pkg(device.launcherPackageName).depth(0)), TIMEOUT)
                }
                step("Open application") {
                    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
                    intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    device.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
                }
            }
        }

    companion object {
        private const val TIMEOUT = 1_000L
    }
}
