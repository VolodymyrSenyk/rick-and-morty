package com.senyk.rickandmorty.presentation.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun RickAndMortyNavHost(rootNavController: NavHostController) {
    NavHost(navController = rootNavController, startDestination = CharactersListDestination) {
        charactersGraph(rootNavController)
    }
}
