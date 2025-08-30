package com.senyk.rickandmorty.presentation.presentation.feature.main.mvi

import arch.mvi.ViewState
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem

internal data class CharactersListViewState(
    val charactersList: List<ListItem> = emptyList(),
    val isRefreshing: Boolean = false,
    val showProgress: Boolean = true,
    val loadingNextDataSet: Boolean = false,
) : ViewState
