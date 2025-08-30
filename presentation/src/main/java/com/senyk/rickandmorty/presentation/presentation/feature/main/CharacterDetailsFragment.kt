package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharacterDetailsBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharacterDetailsIntent
import com.senyk.rickandmorty.presentation.presentation.recycler.adapter.BaseDataBindingDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.CharacterDetailsAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>() {

    override val layoutRes = R.layout.fragment_character_details

    private val viewModel: CharacterDetailsViewModel by viewModels()
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var adapter: BaseDataBindingDelegationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpList()
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onIntent(CharacterDetailsIntent.OnViewStarted(args.character))
    }

    override fun onResume() {
        super.onResume()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = args.character.name
    }

    private fun setObservers() {
        viewModel.uiState.subscribeWithLifecycle { uiState ->
            uiState.characterAvatarUrl?.let { binding.characterAvatarUrl = it }
            adapter.items = uiState.characterData
        }
    }

    private fun setUpList() {
        adapter = BaseDataBindingDelegationAdapter(listOf(CharacterDetailsAdapterDelegate()))
        binding.characterDetailsList.adapter = adapter
    }
}
