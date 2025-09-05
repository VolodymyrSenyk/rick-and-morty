package core.ui.theme.colorscheme

import androidx.compose.material3.lightColorScheme
import core.ui.theme.AppColors

internal val LightMaterialColorScheme = lightColorScheme(
    primary = AppColors.Blue500,
    onPrimary = AppColors.Grey025,

    primaryContainer = AppColors.Blue050,
    onPrimaryContainer = AppColors.Black900,

    secondary = AppColors.Blue300,
    onSecondary = AppColors.Grey025,

    background = AppColors.Grey200,
    onBackground = AppColors.Black900,

    surface = AppColors.Grey025, // main screen cards
    surfaceContainerLowest = AppColors.Grey100, // search results background
    surfaceContainerLow = AppColors.Grey025, // search result card
    surfaceContainerHigh = AppColors.Grey025, // dialogs
    surfaceContainerHighest = AppColors.Grey025, // dropdowns, fields, toggles
    onSurface = AppColors.Black900,

    error = AppColors.Red500,

    outline = AppColors.Grey825, // borders
    outlineVariant = AppColors.Grey300, // faded borders, dividers
)
