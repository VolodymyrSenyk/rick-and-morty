package com.senyk.rickandmorty.testcase

import com.senyk.rickandmorty.core.base.BaseTestCase
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.characters.CheckCharacterFromListScenario
import com.senyk.rickandmorty.scenario.characters.CheckCharactersListScenario
import org.junit.Test

class CharactersFeatureTestCase : BaseTestCase() {

    @Test
    fun charactersNavigation() {
        val defaultList = listOf("Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith", "Jerry Smith", "Abadango Cluster Princess")
        val rickDetails = listOf("Rick Sanchez", "Alive", "Human", "Male", "Earth (C-137)", "Citadel of Ricks")
        val abadangoDetails = listOf("Abadango Cluster Princess", "Alive", "Alien", "Female", "Abadango")
        step("Check characters list and details navigation") {
            scenario(CheckCharactersListScenario(defaultList))
            scenario(CheckCharacterFromListScenario(name = rickDetails.first(), content = rickDetails, navigateBackWithSystemButton = true))
            scenario(CheckCharacterFromListScenario(name = abadangoDetails.first(), content = abadangoDetails))
        }
    }
}
