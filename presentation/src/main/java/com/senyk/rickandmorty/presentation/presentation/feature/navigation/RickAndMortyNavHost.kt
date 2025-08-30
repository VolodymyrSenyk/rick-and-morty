package com.senyk.rickandmorty.presentation.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import feature.characters.navigation.CharactersListDestination
import feature.characters.navigation.charactersGraph

@Composable
fun RickAndMortyNavHost(rootNavController: NavHostController) {
    NavHost(navController = rootNavController, startDestination = CharactersListDestination) {
        charactersGraph(rootNavController)
    }
}
