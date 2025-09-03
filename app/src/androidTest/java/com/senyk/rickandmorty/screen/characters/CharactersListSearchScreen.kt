package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.utils.findIconButton
import com.senyk.rickandmorty.core.utils.findProgressBar
import com.senyk.rickandmorty.core.utils.findText
import feature.characters.ui.R
import core.ui.R as CoreR

class CharactersListSearchScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val menuBack = composeTestRule.findIconButton(CoreR.string.menu_item_back)
    val menuClear = composeTestRule.findIconButton(CoreR.string.menu_item_clear)

    val textInvalidSearchQuery = composeTestRule.findText(R.string.message_empty_state_search)
    val textListEmptyState = composeTestRule.findText(R.string.message_characters_empty_list)

    val progressBar = composeTestRule.findProgressBar()
}
