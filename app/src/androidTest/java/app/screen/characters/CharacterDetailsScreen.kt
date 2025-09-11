package app.screen.characters

import androidx.activity.ComponentActivity
import app.core.base.ActivityComposeTestRule
import app.core.utils.findIconButton
import app.core.utils.findProgressBar
import com.senyk.rickandmorty.core.ui.R

class CharacterDetailsScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val menuBack = composeTestRule.findIconButton(R.string.menu_item_back)
    val menuNightTheme = composeTestRule.findIconButton(R.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(R.string.menu_item_day_theme)

    val progressBar = composeTestRule.findProgressBar()
}
