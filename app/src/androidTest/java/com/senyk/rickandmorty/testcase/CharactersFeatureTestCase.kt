package com.senyk.rickandmorty.testcase

import com.senyk.rickandmorty.core.base.BaseTestCase
import com.senyk.rickandmorty.core.base.StepsLogger.step
import com.senyk.rickandmorty.scenario.characters.CheckCharacterFromListScenario
import com.senyk.rickandmorty.scenario.characters.CheckCharactersListScenario
import com.senyk.rickandmorty.scenario.characters.SortCharactersListScenario
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

    @Test
    fun charactersSorting() {
        val defaultList = listOf("Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith", "Jerry Smith", "Abadango Cluster Princess")
        val ascendingList = listOf("Abadango Cluster Princess", "Abradolf Lincler", "Adjudicator Rick", "Agency Director")
        val descendingList = listOf("Summer Smith", "Rick Sanchez", "Morty Smith", "Jerry Smith")
        step("Check characters list sorting") {
            step("Check default list without sorting") {
                scenario(CheckCharactersListScenario(defaultList))
            }
            step("Check ascending characters list sorting") {
                scenario(SortCharactersListScenario())
                scenario(CheckCharactersListScenario(ascendingList))
            }
            step("Check descending characters list sorting") {
                scenario(SortCharactersListScenario())
                scenario(CheckCharactersListScenario(descendingList))
            }
        }
    }
}
