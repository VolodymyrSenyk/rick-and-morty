package com.senyk.rickandmorty.scenario.characters.details

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.performClick
import com.senyk.rickandmorty.core.base.ActivityComposeTestRule
import com.senyk.rickandmorty.core.base.BaseScenario
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.core.utils.findText
import com.senyk.rickandmorty.core.utils.waitUntilDisplaying
import com.senyk.rickandmorty.core.utils.waitUntilHiding
import com.senyk.rickandmorty.screen.characters.CharacterDetailsScreen

class CheckCharacterDetailsScenario<A : ComponentActivity>(
    private val contentToCheck: List<String>,
) : BaseScenario<A>() {

    override val steps: ActivityComposeTestRule<A>.() -> Unit
        get() = {
            step("Check 'Character Details' content and return back") {
                CharacterDetailsScreen(this).apply {
                    waitUntilHiding(progressBar)
                    contentToCheck.forEachIndexed { index, text ->
                        val ordinalNumber = contentToCheck.subList(0, index).count { it == text }
                        waitUntilDisplaying(findText(text, ordinalNumber))
                        findText(text, ordinalNumber).assertExists()
                    }
                    menuBack.performClick()
                }
            }
        }
}
