package core.ui.utils

import android.content.Context
import android.os.Build

fun isRunningOnEmulator(): Boolean = Build.FINGERPRINT.startsWith("generic") ||
        Build.FINGERPRINT.lowercase().contains("vbox") ||
        Build.FINGERPRINT.lowercase().contains("test-keys") ||
        Build.MODEL.contains("Emulator") ||
        Build.MODEL.contains("Android SDK built for x86") ||
        Build.MANUFACTURER.contains("Genymotion") ||
        Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic") ||
        "google_sdk" == Build.PRODUCT

/**
 * Checks whether the current build is the `uitest` build type.
 *
 * @return `true` if the app is running with the `.uitest` applicationId suffix.
 */
fun Context.isUiTestRunning(): Boolean = packageName.endsWith(".uitest")
