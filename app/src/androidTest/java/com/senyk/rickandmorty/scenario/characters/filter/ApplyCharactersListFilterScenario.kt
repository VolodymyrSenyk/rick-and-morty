package com.senyk.rickandmorty.scenario.characters.filter

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.screen.characters.CharactersListFilterScreen
import domain.characters.model.GenderType
import domain.characters.model.StatusType
import feature.characters.ui.R

class ApplyCharactersListFilterScenario<A : ComponentActivity>(
    private val status: StatusType? = null,
    private val gender: GenderType? = null,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Apply 'Characters List Filter'") {
                CharactersListFilterScreen(this).apply {
                    textTitle.assertExists()

                    findText(R.string.status_all).performClick()
                    val statusDropdownItemTextRes = when (status) {
                        StatusType.ALIVE -> R.string.status_alive
                        StatusType.DEAD -> R.string.status_dead
                        StatusType.UNKNOWN -> R.string.status_unknown
                        null -> R.string.status_all
                    }
                    findText(statusDropdownItemTextRes, index = if (status == null) 1 else 0).performClick()

                    findText(R.string.gender_all).performClick()
                    val genderDropdownItemTextRes = when (gender) {
                        GenderType.FEMALE -> R.string.gender_female
                        GenderType.MALE -> R.string.gender_male
                        GenderType.GENDERLESS -> R.string.gender_female
                        GenderType.UNKNOWN -> R.string.gender_unknown
                        null -> R.string.gender_all
                    }
                    findText(genderDropdownItemTextRes, index = if (gender == null) 1 else 0).performClick()

                    buttonCancel.assertExists()
                    buttonApply.performClick()
                }
            }
        }
}
