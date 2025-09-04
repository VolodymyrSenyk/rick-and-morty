package core.ui.theme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalInspectionMode
import core.ui.theme.colorscheme.DarkMaterialColorScheme
import core.ui.theme.colorscheme.LightMaterialColorScheme
import core.ui.utils.ProvideAnimatedVisibilityScope
import core.ui.utils.ProvideSharedTransitionScope
import domain.settings.model.ThemeMode

val LocalThemeMode = staticCompositionLocalOf { ThemeMode.SYSTEM }

@OptIn(ExperimentalSharedTransitionApi::class)
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
                SharedTransitionLayout {
                    ProvideSharedTransitionScope {
                        if (LocalInspectionMode.current) {
                            AnimatedVisibility(visible = true, label = "LocalAnimatedVisibility") {
                                ProvideAnimatedVisibilityScope(content)
                            }
                        } else {
                            content()
                        }
                    }
                }
            }
        }
    }
}
