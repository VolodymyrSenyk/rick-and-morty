package core.ui.components.topappbar.menu

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.senyk.rickandmorty.core.ui.R
import core.ui.theme.LocalThemeMode
import core.ui.theme.RickAndMortyTheme
import domain.settings.model.ThemeMode

@Composable
fun ThemeChoosingMenuItem(
    modifier: Modifier = Modifier,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    val isDarkMode = when (LocalThemeMode.current) {
        ThemeMode.DARK -> true
        ThemeMode.LIGHT -> false
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
    }

    var rotationAngle by remember { mutableFloatStateOf(0f) }
    val animatedRotation by animateFloatAsState(
        label = "IconRotation",
        targetValue = rotationAngle,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
    )

    Crossfade(
        label = "IconCrossfade",
        targetState = isDarkMode,
        animationSpec = tween(durationMillis = 500),
    ) { themeMode ->
        IconButton(
            onClick = {
                val newThemeMode = if (isDarkMode) ThemeMode.LIGHT else ThemeMode.DARK
                if (rotationAngle < Float.MAX_VALUE - 360f) rotationAngle += 360f
                onThemeSelected(newThemeMode)
            },
            modifier = modifier
        ) {
            val iconRes = if (themeMode) R.drawable.ic_night else R.drawable.ic_day
            val contentDescription = if (themeMode) {
                R.string.menu_item_night_theme
            } else {
                R.string.menu_item_day_theme
            }
            Icon(
                imageVector = ImageVector.vectorResource(iconRes),
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = stringResource(contentDescription),
                modifier = Modifier.rotate(animatedRotation),
            )
        }
    }
}

@Preview
@Composable
private fun ThemeChoosingMenuItemPreview() {
    RickAndMortyTheme {
        ThemeChoosingMenuItem(onThemeSelected = {})
    }
}
