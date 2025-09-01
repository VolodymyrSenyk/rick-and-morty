package feature.characters.model

import domain.characters.model.CharacterDto

data class CharacterUi(
    val id: String,
    val name: String,
    val imageUrl: String,
)

internal fun CharacterDto.toCharacterUi(): CharacterUi = CharacterUi(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
