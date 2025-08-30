package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.senyk.rickandmorty.presentation.presentation.feature.main.list.CharactersListScreen
import core.ui.theme.RickAndMortyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment : Fragment() {

    private val directions = CharactersListFragmentDirections
    private val viewModel: CharactersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(context = requireContext()).apply {
        setContent {
            RickAndMortyTheme {
                CharactersListScreen(
                    viewModel = viewModel,
                    navigateToCharacterDetails = {
                        val direction = directions.actionCharactersListFragmentToCharacterDetailsFragment(it)
                        findNavController().navigate(direction)
                    },
                    navigateBack = {
                        findNavController().popBackStack()
                    }
                )
            }
        }
    }
}
