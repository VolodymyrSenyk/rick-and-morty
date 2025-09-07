package com.senyk.rickandmorty.screen.characters

import androidx.activity.ComponentActivity
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.core.utils.findTextButton
import feature.characters.ui.R
import core.ui.R as CoreR

class CharactersListFilterScreen<A : ComponentActivity>(composeTestRule: ActivityComposeTestRule<A>) {

    val textTitle = composeTestRule.findText(R.string.dialog_title_characters_list_filter_settings)

    val buttonCancel = composeTestRule.findTextButton(CoreR.string.dialog_answer_cancel)
    val buttonApply = composeTestRule.findTextButton(CoreR.string.dialog_answer_apply)
}
