package com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.senyk.rickandmorty.presentation.databinding.ListItemCharacterBinding
import com.senyk.rickandmorty.presentation.presentation.entity.CharacterUi
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem
import com.senyk.rickandmorty.presentation.presentation.feature.main.CharactersListViewModel
import com.senyk.rickandmorty.presentation.presentation.feature.main.mvi.CharactersListIntent
import com.senyk.rickandmorty.presentation.presentation.recycler.viewholder.BaseDataBindingViewHolder

internal class CharacterAdapterDelegate(
    private val viewModel: CharactersListViewModel
) : AdapterDelegate<List<ListItem>>() {

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean =
        items[position] is CharacterUi

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemCharacterBinding.inflate(inflater, parent, false)
        return BaseDataBindingViewHolder<ListItemCharacterBinding>(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val viewHolder = holder as? BaseDataBindingViewHolder<ListItemCharacterBinding> ?: return
        val model = items[position] as? CharacterUi ?: return
        viewHolder.binding?.apply {
            this.model = model
            this.viewModel = this@CharacterAdapterDelegate.viewModel
        }
    }

    companion object {

        @JvmStatic
        fun onCharacterClicked(character: CharacterUi): CharactersListIntent =
            CharactersListIntent.OnCharacterClicked(character)
    }
}
