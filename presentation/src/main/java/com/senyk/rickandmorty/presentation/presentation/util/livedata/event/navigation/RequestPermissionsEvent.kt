package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

data class RequestPermissionsEvent(
    val permissions: List<String>,
    val requestCode: Int
) : NavigationEvent
