package core.ui.theme.colorscheme

import androidx.compose.material3.darkColorScheme
import core.ui.theme.AppColors

internal val DarkMaterialColorScheme = darkColorScheme(
    primary = AppColors.Black900,
    onPrimary = AppColors.Grey100,

    secondary = AppColors.Grey750,

    background = AppColors.Grey850,
    onBackground = AppColors.Grey100,

    surface = AppColors.Grey750,
    surfaceVariant = AppColors.Grey600,
    onSurface = AppColors.Grey100,

    error = AppColors.Red600,

    outline = AppColors.Grey600,
)
