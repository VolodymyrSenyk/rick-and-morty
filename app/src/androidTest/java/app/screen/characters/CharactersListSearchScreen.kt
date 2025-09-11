package app.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.feature.characters.ui.R
import uitestutil.compose.findIconButton
import uitestutil.compose.findProgressBar
import uitestutil.compose.findText
import uitestutil.compose.scenario.ActivityComposeTestRule
import com.senyk.rickandmorty.core.ui.R as CoreR

class CharactersListSearchScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val menuBack = composeTestRule.findIconButton(CoreR.string.menu_item_back)
    val menuClear = composeTestRule.findIconButton(CoreR.string.menu_item_clear)

    val textListEmptyState = composeTestRule.findText(R.string.message_empty_state_characters_list)

    val progressBar = composeTestRule.findProgressBar()
}
