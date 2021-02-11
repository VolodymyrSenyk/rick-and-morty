package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharactersListBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.recycler.adapter.BaseDataBindingDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.CharacterAdapterDelegate
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.EmptyStateAdapterDelegate
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.ProgressAdapterDelegate

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
        viewModel.scrollToTop.observe(viewLifecycleOwner, { scroll ->
            if (scroll) {
                (binding.charactersList.layoutManager as? GridLayoutManager)?.scrollToPosition(0)
            }
        })
    }

    private fun setUpList() {
        adapter = BaseDataBindingDelegationAdapter(
            listOf(
                CharacterAdapterDelegate(viewModel),
                ProgressAdapterDelegate(),
                EmptyStateAdapterDelegate()
            )
        )
        val layoutManager = GridLayoutManager(requireContext(), 2)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int = when (adapter.getItem(position)) {
                is CharacterUi -> 1
                else -> layoutManager.spanCount
            }
        }
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                layoutManager.findLastVisibleItemPosition().let { viewModel.onScroll(it) }
                super.onScrolled(recyclerView, dx, dy)
            }
        }
        binding.charactersList.apply {
            this.adapter = this@CharactersListFragment.adapter
            this.layoutManager = layoutManager
            addOnScrollListener(scrollListener)
        }
        binding.swipeRefreshCharacters.setOnRefreshListener { viewModel.onRefresh() }
    }
}
