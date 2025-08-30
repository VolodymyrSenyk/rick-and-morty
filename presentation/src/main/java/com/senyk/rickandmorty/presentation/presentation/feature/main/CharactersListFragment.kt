package com.senyk.rickandmorty.presentation.presentation.feature.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.senyk.rickandmorty.presentation.R
import com.senyk.rickandmorty.presentation.databinding.FragmentCharactersListBinding
import com.senyk.rickandmorty.presentation.presentation.base.BaseFragment
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListIntent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListNavEvent
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListSideEffect
import com.senyk.rickandmorty.presentation.presentation.recycler.adapter.BaseDataBindingDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.CharacterAdapterDelegate
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.EmptyStateAdapterDelegate
import com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate.ProgressAdapterDelegate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersListFragment : BaseFragment<FragmentCharactersListBinding>() {

    override val layoutRes = R.layout.fragment_characters_list
    override val menuRes = R.menu.menu_main

    private val directions = CharactersListFragmentDirections

    private val viewModel: CharactersListViewModel by viewModels()

    private lateinit var adapter: BaseDataBindingDelegationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        setUpList()
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onIntent(CharactersListIntent.OnViewStarted)
    }

    override fun onResume() {
        super.onResume()
        setUpToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {

            R.id.action_sort -> {
                viewModel.onIntent(CharactersListIntent.OnSortClicked); true
            }

            else -> super.onOptionsItemSelected(item)
        }

    private fun setUpToolbar() {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.title =
            getString(R.string.app_name)
    }

    private fun setObservers() {
        viewModel.uiState.subscribeWithLifecycle { uiState ->
            adapter.items = uiState.charactersList
            binding.swipeRefreshCharacters.isRefreshing = uiState.isRefreshing
        }
        viewModel.sideEffect.subscribeWithLifecycle { mviSideEffect ->
            when (mviSideEffect) {
                is CharactersListSideEffect.ShowErrorMessage -> {
                    val message = requireContext().getString(R.string.error_unknown)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                }

                is CharactersListSideEffect.ScrollToTop -> {
                    binding.charactersList.layoutManager?.scrollToPosition(0)
                }

                null -> {}
            }
            viewModel.onSideEffectHandled(mviSideEffect)
        }
        viewModel.navEvent.subscribeWithLifecycle { mviNavEvent ->
            when (mviNavEvent) {
                is CharactersListNavEvent.NavigateToCharacterDetails -> {
                    val direction = directions.actionCharactersListFragmentToCharacterDetailsFragment(character = mviNavEvent.character)
                    findNavController().navigate(direction)
                }

                is CharactersListNavEvent.NavigateBack -> findNavController().popBackStack()

                null -> {}
            }
            viewModel.onNavEventHandled(mviNavEvent)
        }
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
                layoutManager.findLastVisibleItemPosition().let { viewModel.onIntent(CharactersListIntent.OnScrolled(it)) }
                super.onScrolled(recyclerView, dx, dy)
            }
        }
        binding.charactersList.apply {
            this.adapter = this@CharactersListFragment.adapter
            this.layoutManager = layoutManager
            addOnScrollListener(scrollListener)
        }
        binding.swipeRefreshCharacters.setOnRefreshListener { viewModel.onIntent(CharactersListIntent.OnRefreshed) }
    }
}
