package feature.characters.presentation.model

import domain.characters.model.GenderType
import domain.characters.model.StatusType

data class CharactersListFilter(
    val statusType: StatusType? = null,
    val genderType: GenderType? = null,
)
