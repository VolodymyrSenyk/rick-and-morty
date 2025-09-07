package core.ui.theme.colorscheme

import androidx.compose.material3.darkColorScheme
import core.ui.theme.AppColors

internal val DarkMaterialColorScheme = darkColorScheme(
    primary = AppColors.Black900,
    onPrimary = AppColors.Grey200,

    primaryContainer = AppColors.Grey900,
    onPrimaryContainer = AppColors.Grey300,

    secondary = AppColors.Grey600,
    onSecondary = AppColors.Grey300,

    background = AppColors.Grey900,
    onBackground = AppColors.Grey300,

    surface = AppColors.Grey875, // main screen cards
    surfaceDim = AppColors.Grey875, // placeholders
    surfaceContainerLowest = AppColors.Grey950, // search results background
    surfaceContainerLow = AppColors.Grey875, // search result card
    surfaceContainerHigh = AppColors.Grey875, // dialogs
    surfaceContainerHighest = AppColors.Grey850, // dropdowns, fields, toggles
    onSurface = AppColors.Grey200,

    error = AppColors.Red600,

    outline = AppColors.Black900, // borders
    outlineVariant = AppColors.Black900, // faded borders, dividers
)
