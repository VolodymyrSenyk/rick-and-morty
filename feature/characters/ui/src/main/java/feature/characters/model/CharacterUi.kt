package feature.characters.model

import domain.characters.model.CharacterDto

data class CharacterUi(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String,
) {

    companion object {

        val EMPTY = CharacterUi(
            id = System.currentTimeMillis().toString(),
            name = "",
            status = "",
            species = "",
            type = "",
            gender = "",
            origin = "",
            location = "",
            imageUrl = "",
        )
    }
}

internal fun CharacterDto.toCharacterUi(): CharacterUi = CharacterUi(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    origin = origin,
    location = location,
    imageUrl = imageUrl,
)
