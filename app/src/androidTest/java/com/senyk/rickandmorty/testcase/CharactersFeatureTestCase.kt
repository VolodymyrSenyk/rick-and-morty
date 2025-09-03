package com.senyk.rickandmorty.testcase

import com.senyk.rickandmorty.core.base.BaseTestCase
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.characters.details.CheckCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.characters.list.CheckCharactersListScenario
import com.senyk.rickandmorty.scenario.characters.list.OpenCharacterDetailsScenario
import com.senyk.rickandmorty.scenario.system.WaitUntilStartScenario
import org.junit.Test

class CharactersFeatureTestCase : BaseTestCase() {

    @Test
    fun charactersNavigation() {
        val defaultList = listOf("Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith", "Jerry Smith")
        val rickDetails = listOf("Rick Sanchez", "Alive", "Human", "Male", "Earth (C-137)", "Citadel of Ricks")
        val abadangoDetails = listOf("Abadango Cluster Princess", "Alive", "Alien", "Female", "Abadango")
        scenario(WaitUntilStartScenario())
        step("Check 'Characters List' and 'Character Details' content and navigation") {
            scenario(CheckCharactersListScenario(defaultList))
            scenario(OpenCharacterDetailsScenario("Rick Sanchez"))
            scenario(CheckCharacterDetailsScenario(rickDetails))
            scenario(WaitUntilStartScenario())
            scenario(OpenCharacterDetailsScenario("Abadango Cluster Princess"))
            scenario(CheckCharacterDetailsScenario(abadangoDetails))
        }
    }
}
