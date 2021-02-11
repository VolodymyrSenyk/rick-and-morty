package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharacterDetailsBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment
import com.senyk.rickandmorty.presentation.presentation.recycler.adapter.BaseDataBindingDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.CharacterDetailsAdapterDelegate

class CharacterDetailsFragment : BaseFragment<FragmentCharacterDetailsBinding>() {

    override val layoutRes = R.layout.fragment_character_details
    override val viewModel by viewModels<CharacterDetailsViewModel>(factoryProducer = { viewModelFactory })
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private lateinit var adapter: BaseDataBindingDelegationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.model = args.character
        viewModel.setCharacter(args.character)
        setUpList()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title = args.character.name
    }

    private fun setObservers() {
        viewModel.characterDetails.observe(viewLifecycleOwner, { list ->
            adapter.items = list
        })
    }

    private fun setUpList() {
        adapter = BaseDataBindingDelegationAdapter(listOf(CharacterDetailsAdapterDelegate()))
        binding.characterDetailsList.adapter = adapter
    }
}
