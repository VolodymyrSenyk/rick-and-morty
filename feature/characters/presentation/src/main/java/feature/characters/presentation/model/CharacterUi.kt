package feature.characters.presentation.model

import domain.characters.model.Character

data class CharacterUi(
    val id: String,
    val uiId: String,
    val name: String,
    val imageUrl: String,
)

internal fun Character.toCharacterUi(idPrefix: String): CharacterUi = CharacterUi(
    id = id,
    uiId = idPrefix + id,
    name = name,
    imageUrl = imageUrl,
)
