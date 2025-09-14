package app.screen.imageviewer

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.ui.R
import uitestutil.compose.findIconButton
import uitestutil.compose.scenario.ActivityComposeTestRule

class ImageViewerScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {
    val menuBack = composeTestRule.findIconButton(R.string.menu_item_back)
    val menuNightTheme = composeTestRule.findIconButton(R.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(R.string.menu_item_day_theme)
}
