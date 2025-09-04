package core.ui.theme.colorscheme

import androidx.compose.material3.lightColorScheme
import core.ui.theme.AppColors

internal val LightMaterialColorScheme = lightColorScheme(
    primary = AppColors.Blue500,
    onPrimary = AppColors.Grey050,

    secondary = AppColors.Blue300,

    background = AppColors.Grey075,
    onBackground = AppColors.Black900,

    surface = AppColors.Grey050,
    surfaceVariant = AppColors.Blue075,
    onSurface = AppColors.Black900,

    error = AppColors.Red500,

    outline = AppColors.Grey500,
)
