@file:Suppress("UnusedReceiverParameter")

package core.ui.theme.colorscheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import core.ui.theme.AppColors
import core.ui.theme.LocalThemeMode
import domain.settings.model.ThemeMode

@Composable
fun isDarkMode(): Boolean = when (LocalThemeMode.current) {
    ThemeMode.DARK -> true
    ThemeMode.LIGHT -> false
    ThemeMode.SYSTEM -> isSystemInDarkTheme()
}

val ColorScheme.navigationBar: Color
    get() = AppColors.Grey950

val ColorScheme.onPrimaryHint: Color
    @Composable
    get() = if (isDarkMode()) {
        AppColors.Black900
    } else {
        AppColors.Blue500
    }.copy(alpha = 0.4f)
