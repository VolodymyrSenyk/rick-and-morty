package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.util.findIconButton
import com.senyk.rickandmorty.core.util.findListItemWithIndex
import com.senyk.rickandmorty.core.util.findProgressBar
import com.senyk.rickandmorty.core.util.findText
import feature.characters.ui.R
import core.ui.R as CoreR

class CharactersListScreen<A : ComponentActivity>(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>) {

    val textTitle = composeTestRule.findText(CoreR.string.app_name)

    val menuSearch = composeTestRule.findIconButton(R.string.menu_search)
    val menuNightTheme = composeTestRule.findIconButton(CoreR.string.menu_item_night_theme)
    val menuDayTheme = composeTestRule.findIconButton(CoreR.string.menu_item_day_theme)
    val menuFilter = composeTestRule.findIconButton(R.string.menu_filter)

    val textListEmptyState = composeTestRule.findText(R.string.message_characters_empty_list)

    val progressBar = composeTestRule.findProgressBar()

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textListItem(text: String) = findText(text)

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textListItem(
        index: Int,
        text: String,
    ) = findListItemWithIndex(index, text)
}
