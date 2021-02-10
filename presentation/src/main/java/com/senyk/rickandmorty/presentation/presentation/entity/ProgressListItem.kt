package com.senyk.rickandmorty.presentation.presentation.entity

class ProgressListItem : ListItem {
    override val viewType: Int = this::class.hashCode()
    override val listId: String = this::class.hashCode().toString()
}
