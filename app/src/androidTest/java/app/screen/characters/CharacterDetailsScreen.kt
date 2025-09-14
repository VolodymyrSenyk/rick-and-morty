package app.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.feature.characters.ui.R
import uitestutil.compose.findIconButton
import uitestutil.compose.findImage
import uitestutil.compose.findProgressBar
import uitestutil.compose.scenario.ActivityComposeTestRule
import com.senyk.rickandmorty.core.ui.R as CoreR

class CharacterDetailsScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val menuBack = composeTestRule.findIconButton(CoreR.string.menu_item_back)
    val menuNightTheme = composeTestRule.findIconButton(CoreR.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(CoreR.string.menu_item_day_theme)

    val progressBar = composeTestRule.findProgressBar()
    val characterImage = composeTestRule.findImage(R.string.content_description_character_image)
}
