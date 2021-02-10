package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharacterDetailsBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment

class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>() {

    override val layoutRes = R.layout.fragment_character_details
    override val viewModel by viewModels<CharacterDetailsViewModel>(factoryProducer = { viewModelFactory })
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        viewModel.setCharacter(args.character)
    }
}
