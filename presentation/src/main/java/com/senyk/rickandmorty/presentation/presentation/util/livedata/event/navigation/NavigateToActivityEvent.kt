package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

import android.app.Activity

data class NavigateToActivityEvent(
    val destination: Class<out Activity>,
    val finishCurrentActivity: Boolean = false
) : NavigationEvent
