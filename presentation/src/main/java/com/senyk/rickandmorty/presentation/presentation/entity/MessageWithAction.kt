package com.senyk.rickandmorty.presentation.presentation.entity

import android.view.View

data class MessageWithAction(
    val text: String,
    val actionName: String,
    val action: View.OnClickListener
)
