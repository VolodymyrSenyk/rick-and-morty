package com.senyk.rickandmorty.presentation.presentation.feature.navigation

import com.senyk.rickandmorty.presentation.presentation.feature.navigation.args.CharacterNavArg
import kotlinx.serialization.Serializable
import navigation.compose.Destination

@Serializable
object CharactersListDestination : Destination

@Serializable
data class CharacterDetailsDestination(val character: CharacterNavArg) : Destination
