package core.ui.utils

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@Composable
fun heightWithCoef(heightCoef: Float): Dp {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return with(density) { windowInfo.containerSize.height.toDp() * heightCoef }
}

@ExperimentalMaterial3WindowSizeClassApi
@Composable
fun isLandscape(): Boolean {
    val windowSizeClass = calculateWindowSizeClass(activity = LocalContext.current as Activity)
    return when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> false
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> true
        else -> false
    }
}
