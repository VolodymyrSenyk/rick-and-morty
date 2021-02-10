package com.senyk.rickandmorty.domain.entity

data class CharacterDto(
    val id: Int,
    val name: String,
    val status: StatusType,
    val species: String,
    val type: String,
    val gender: GenderType,
    val origin: String,
    val location: String,
    val imageUrl: String
)
