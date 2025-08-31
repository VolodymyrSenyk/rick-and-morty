package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.senyk.rickandmorty.core.util.findIconButton
import com.senyk.rickandmorty.core.util.findListItem
import core.ui.R

class CharacterDetailsScreen<A : ComponentActivity>(composeTestRule: AndroidComposeTestRule<ActivityScenarioRule<A>, A>) {

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textTitle(text: String) = findListItem(0, text)

    val menuBack = composeTestRule.findIconButton(R.string.menu_item_back)

    fun AndroidComposeTestRule<ActivityScenarioRule<A>, A>.textListItem(
        index: Int,
        text: String,
    ) = findListItem(index, text)
}
