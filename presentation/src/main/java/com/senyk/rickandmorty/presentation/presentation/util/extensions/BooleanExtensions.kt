package com.senyk.rickandmorty.presentation.presentation.util.extensions

inline val Boolean?.int get() = if (this == true) 1 else 0
