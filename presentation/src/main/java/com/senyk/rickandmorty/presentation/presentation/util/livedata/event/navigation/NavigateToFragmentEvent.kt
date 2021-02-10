package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

import androidx.navigation.NavDirections

data class NavigateToFragmentEvent(val action: NavDirections) : NavigationEvent
