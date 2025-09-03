package feature.characters.presentation.viewmodel.utlis

import domain.characters.model.Character
import feature.characters.presentation.model.CharacterUi

class CharacterListTestData(
    val startIndex: Int,
    val pageSize: Int = DEFAULT_PAGE_SIZE,
) {

    val character = Character(
        id = "1",
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = "Earth (C-137)",
        location = "Citadel of Ricks",
        imageUrl = "someUrl",
    )

    val characterUi = CharacterUi(
        id = "1",
        name = "Rick Sanchez",
        imageUrl = "someUrl",
    )

    val charactersList = List(pageSize) { character }.mapIndexed { index, model ->
        model.copy(id = (index + startIndex).toString())
    }

    val charactersUiList = List(pageSize) { characterUi }.mapIndexed { index, model ->
        model.copy(id = (index + startIndex).toString())
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}
