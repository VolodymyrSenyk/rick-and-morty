package core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import core.ui.theme.colorscheme.DarkMaterialColorScheme
import core.ui.theme.colorscheme.LightMaterialColorScheme
import domain.settings.model.ThemeMode

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

@Composable
fun RickAndMortyTheme(
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
        ) {
            val customTextSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colorScheme.secondary,
                backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f),
            )
            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors,
            ) {
                content()
            }
        }
    }
}
