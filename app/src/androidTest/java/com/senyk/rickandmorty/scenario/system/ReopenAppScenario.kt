package com.senyk.rickandmorty.scenario.system

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.util.getTargetContext
import com.senyk.rickandmorty.core.util.getUiDevice

class ReopenAppScenario<A : ComponentActivity>() : BaseScenario<A>() {

    override val steps: AndroidComposeTestRule<ActivityScenarioRule<A>, A>.() -> Unit
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
