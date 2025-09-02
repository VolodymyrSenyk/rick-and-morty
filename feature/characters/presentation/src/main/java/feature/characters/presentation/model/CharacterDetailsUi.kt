package feature.characters.presentation.model

import domain.characters.model.Character

data class CharacterDetailsUi(
    val id: String,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: String,
    val location: String,
    val imageUrl: String,
)

internal fun Character.toCharacterDetailsUi(): CharacterDetailsUi = CharacterDetailsUi(
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
