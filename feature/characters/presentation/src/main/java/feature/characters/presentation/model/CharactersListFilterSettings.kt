package feature.characters.presentation.model

import domain.characters.model.GenderType
import domain.characters.model.StatusType

data class CharactersListFilterSettings(
    val statusType: StatusType?,
    val genderType: GenderType?,
) {

    companion object {
        val EMPTY = CharactersListFilterSettings(
            statusType = null,
            genderType = null,
        )
    }
}
