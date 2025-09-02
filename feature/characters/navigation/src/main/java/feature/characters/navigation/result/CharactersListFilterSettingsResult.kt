package feature.characters.navigation.result

import domain.characters.model.GenderType
import domain.characters.model.StatusType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import navigation.compose.NavArg

@Parcelize
@Serializable
data class CharactersListFilterSettingsResult(
    val statusType: StatusType?,
    val genderType: GenderType?,
) : NavArg
