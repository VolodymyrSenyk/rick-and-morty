package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.utils.findIconButton
import com.senyk.rickandmorty.core.utils.findProgressBar
import com.senyk.rickandmorty.core.utils.findText
import feature.characters.ui.R
import core.ui.R as CoreR

class CharactersListScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val textTitle = composeTestRule.findText(CoreR.string.app_name)

    val menuSearch = composeTestRule.findIconButton(R.string.menu_search)
    val menuNightTheme = composeTestRule.findIconButton(CoreR.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(CoreR.string.menu_item_day_theme)
    val menuFilter = composeTestRule.findIconButton(R.string.menu_filter)

    val textListEmptyState = composeTestRule.findText(R.string.message_characters_empty_list)

    val progressBar = composeTestRule.findProgressBar()
}
