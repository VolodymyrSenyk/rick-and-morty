package com.senyk.rickandmorty.presentation.presentation.util.livedata.event.navigation

import android.content.Intent

data class StartActivityForResultEvent(
    val intent: Intent,
    val requestCode: Int
) : NavigationEvent
