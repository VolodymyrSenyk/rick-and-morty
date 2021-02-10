package com.senyk.rickandmorty.presentation.presentation.entity

data class EmptyStateListItem(
    val message: String? = null
) : ListItem {
    override val viewType: Int = this::class.hashCode()
    override val listId: String = this::class.hashCode().toString()
}
