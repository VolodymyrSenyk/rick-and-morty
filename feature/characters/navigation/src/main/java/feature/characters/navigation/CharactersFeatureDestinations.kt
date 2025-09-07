package feature.characters.navigation

import domain.characters.model.GenderType
import domain.characters.model.StatusType
import feature.characters.navigation.args.CharacterNavArg
import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
object CharactersListDestination : Destination

@Serializable
class CharactersListFilterDestination(
    val status: StatusType?,
    val gender: GenderType?,
) : Destination

@Serializable
data class CharacterDetailsDestination(val character: CharacterNavArg) : Destination
