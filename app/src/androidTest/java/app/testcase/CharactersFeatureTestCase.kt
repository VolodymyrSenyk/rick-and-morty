package app.testcase

import app.core.base.BaseTestCase
import app.core.base.StepsLogger.step
import app.scenario.characters.details.CheckCharacterDetailsScenario
import app.scenario.characters.filter.ApplyCharactersListFilterScenario
import app.scenario.characters.filter.CancelCharactersListFilterScenario
import app.scenario.characters.list.CheckCharactersListScenario
import app.scenario.characters.list.OpenCharacterDetailsScenario
import app.scenario.characters.list.OpenCharactersListFilterScenario
import app.scenario.characters.list.OpenCharactersSearchScenario
import app.scenario.characters.search.CheckCharactersListSearchClearingScenario
import app.scenario.characters.search.CheckCharactersListSearchScenario
import app.scenario.characters.search.CloseCharactersListSearchScenario
import app.scenario.system.WaitUntilStartScenario
import domain.characters.model.GenderType
import domain.characters.model.StatusType
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
            scenario(OpenCharacterDetailsScenario("Abadango Cluster Princess"))
            scenario(CheckCharacterDetailsScenario(abadangoDetails))
        }
    }

    @Test
    fun charactersListSearch() {
        val defaultList = listOf("Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith", "Jerry Smith")
        val searchResultList = listOf("Tickets Please Guy", "Ticktock", "Sticky")
        scenario(WaitUntilStartScenario())
        step("Check characters list searching feature") {
            scenario(OpenCharactersSearchScenario())
            scenario(CheckCharactersListSearchScenario())
            scenario(CheckCharactersListSearchScenario("Tick", searchResultList))
            scenario(CheckCharactersListSearchClearingScenario())
            scenario(CloseCharactersListSearchScenario())
            scenario(CheckCharactersListScenario(defaultList))
        }
    }

    @Test
    fun charactersListFiltration() {
        val defaultList = listOf("Rick Sanchez", "Morty Smith", "Summer Smith", "Beth Smith", "Jerry Smith")
        val filteredList = listOf("Summer Smith", "Beth Smith", "Abadango Cluster Princess", "Annie")
        scenario(WaitUntilStartScenario())
        step("Check characters list filtration") {
            scenario(CheckCharactersListScenario(defaultList))
            scenario(OpenCharactersListFilterScenario())
            scenario(CancelCharactersListFilterScenario())
            scenario(OpenCharactersListFilterScenario())
            scenario(ApplyCharactersListFilterScenario(gender = GenderType.FEMALE, status = StatusType.ALIVE))
            scenario(CheckCharactersListScenario(filteredList))
        }
    }
}
