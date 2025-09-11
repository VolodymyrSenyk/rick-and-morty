package app.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.ui.R
import uitestutil.compose.findIconButton
import uitestutil.compose.findProgressBar
import uitestutil.compose.scenario.ActivityComposeTestRule

class CharacterDetailsScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val menuBack = composeTestRule.findIconButton(R.string.menu_item_back)
    val menuNightTheme = composeTestRule.findIconButton(R.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(R.string.menu_item_day_theme)

    val progressBar = composeTestRule.findProgressBar()
}
