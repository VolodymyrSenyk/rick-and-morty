package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharactersListBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment
import com.senyk.rickandmorty.presentation.presentation.recycler.adapter.BaseDataBindingDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.CharacterAdapterDelegate
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.EmptyStateAdapterDelegate

class CharactersListFragment : BaseFragment<FragmentCharactersListBinding>() {

    override val layoutRes = R.layout.fragment_characters_list
    override val viewModel by viewModels<CharactersListViewModel>(factoryProducer = { viewModelFactory })

    private lateinit var ordersAdapter: BaseDataBindingDelegationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setUpList()
        setObservers()
    }

    private fun setObservers() {
        viewModel.charactersList.observe(viewLifecycleOwner, { orders ->
            ordersAdapter.items = orders
            if (binding.swipeRefreshCharacters.isRefreshing) {
                binding.swipeRefreshCharacters.isRefreshing = false
            }
        })
    }

    private fun setUpList() {
        ordersAdapter = BaseDataBindingDelegationAdapter(
            listOf(CharacterAdapterDelegate(viewModel), EmptyStateAdapterDelegate())
        )
        binding.charactersList.adapter = ordersAdapter
        binding.swipeRefreshCharacters.setOnRefreshListener { viewModel.onRefresh() }
    }
}
