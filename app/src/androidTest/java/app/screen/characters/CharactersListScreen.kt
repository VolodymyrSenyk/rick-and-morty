package app.screen.characters

import androidx.activity.ComponentActivity
import app.core.base.ActivityComposeTestRule
import app.core.utils.findIconButton
import app.core.utils.findProgressBar
import app.core.utils.findText
import com.senyk.rickandmorty.feature.characters.ui.R
import com.senyk.rickandmorty.core.ui.R as CoreR

class CharactersListScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val textTitle = composeTestRule.findText(CoreR.string.app_name)

    val menuSearch = composeTestRule.findIconButton(R.string.menu_item_search)
    val menuNightTheme = composeTestRule.findIconButton(CoreR.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(CoreR.string.menu_item_day_theme)
    val menuFilter = composeTestRule.findIconButton(R.string.menu_item_filter)

    val textListEmptyState = composeTestRule.findText(R.string.message_empty_state_characters_list)

    val progressBar = composeTestRule.findProgressBar()
}
