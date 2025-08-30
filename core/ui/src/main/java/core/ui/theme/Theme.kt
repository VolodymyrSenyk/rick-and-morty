package core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import core.ui.theme.colorscheme.DarkMaterialColorScheme
import core.ui.theme.colorscheme.LightMaterialColorScheme

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

enum class ThemeMode {
    SYSTEM, LIGHT, DARK,
}

@Composable
fun BlokNotTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT, // Default value for Compose previews
    content: @Composable () -> Unit,
) {
    val isDarkMode = when (themeMode) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }
    val colorScheme = if (isDarkMode) DarkMaterialColorScheme else LightMaterialColorScheme

    CompositionLocalProvider(LocalThemeMode provides themeMode) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}
