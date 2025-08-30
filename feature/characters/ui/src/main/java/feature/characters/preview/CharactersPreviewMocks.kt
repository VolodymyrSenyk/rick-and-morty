package feature.characters.preview

import feature.characters.model.CharacterUi

internal object CharactersPreviewMocks {

    fun avatarConstructor(index: Int = 0): String =
        "https://rickandmortyapi.com/api/character/avatar/${index + 1}.jpeg"

    val character = CharacterUi(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = "Earth (C-137)",
        location = "Citadel of Ricks",
        imageUrl = avatarConstructor(),
    )

    val charactersList: List<CharacterUi> = List(5) { character }.mapIndexed { index, model ->
        model.copy(id = index, imageUrl = avatarConstructor(index))
    }
}
