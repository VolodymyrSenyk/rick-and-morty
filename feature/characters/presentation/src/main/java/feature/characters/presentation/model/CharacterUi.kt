package feature.characters.presentation.model

import domain.characters.model.Character

data class CharacterUi(
    val id: String,
    val name: String,
    val imageUrl: String,
)

internal fun Character.toCharacterUi(): CharacterUi = CharacterUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
