package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

import android.content.Intent

data class StartServiceEvent(
    val intent: Intent
) : NavigationEvent
