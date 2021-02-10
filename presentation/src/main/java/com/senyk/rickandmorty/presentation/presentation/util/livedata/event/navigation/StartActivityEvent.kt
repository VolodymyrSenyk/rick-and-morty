package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

import android.content.Intent

data class StartActivityEvent(val intent: Intent) : NavigationEvent
