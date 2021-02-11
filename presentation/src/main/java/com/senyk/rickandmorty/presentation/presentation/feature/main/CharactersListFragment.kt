package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    private lateinit var adapter: BaseDataBindingDelegationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setUpList()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        setUpToolbar()
    }

    private fun setUpToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.app_name)
    }

    private fun setObservers() {
        viewModel.charactersList.observe(viewLifecycleOwner, { list ->
            adapter.items = list
            if (binding.swipeRefreshCharacters.isRefreshing) {
                binding.swipeRefreshCharacters.isRefreshing = false
            }
        })
    }

    private fun setUpList() {
        adapter = BaseDataBindingDelegationAdapter(
            listOf(CharacterAdapterDelegate(viewModel), EmptyStateAdapterDelegate())
        )
        binding.charactersList.adapter = adapter
        binding.swipeRefreshCharacters.setOnRefreshListener { viewModel.onRefresh() }
    }
}
