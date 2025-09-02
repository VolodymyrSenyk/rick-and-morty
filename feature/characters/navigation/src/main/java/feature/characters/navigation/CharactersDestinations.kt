package feature.characters.navigation

import domain.characters.model.GenderType
import domain.characters.model.StatusType
import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
object CharactersListDestination : Destination

@Serializable
class CharactersListFilterSettingsDestination(
    val status: StatusType?,
    val gender: GenderType?,
) : Destination

@Serializable
data class CharacterDetailsDestination(val characterId: String) : Destination
