package com.senyk.rickandmorty.scenario.characters.filter

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.feature.characters.ui.R
import com.senyk.rickandmorty.screen.characters.CharactersListFilterScreen

class CancelCharactersListFilterScenario<A : ComponentActivity> : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Apply 'Characters List Filter'") {
                CharactersListFilterScreen(this).apply {
                    textTitle.assertExists()
                    findText(R.string.status_all).assertExists()
                    findText(R.string.gender_all).assertExists()
                    buttonApply.assertExists()
                    buttonCancel.performClick()
                }
            }
        }
}
