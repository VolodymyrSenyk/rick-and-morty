package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.util.findIconButton
import com.senyk.rickandmorty.core.util.findListItemWithIndex
import com.senyk.rickandmorty.core.util.findText
import feature.characters.R
import core.ui.R as CoreR

class CharactersListScreen<A : ComponentActivity>(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>) {

    val textTitle = composeTestRule.findText(CoreR.string.app_name)

    val menuSort = composeTestRule.findIconButton(R.string.menu_sort)

    val textListEmptyState = composeTestRule.findText(R.string.message_characters_empty_list)

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textListItem(text: String) = findText(text)

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textListItem(
        index: Int,
        text: String,
    ) = findListItemWithIndex(index, text)
}
