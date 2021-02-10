package com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.senyk.rickandmorty.presentation.databinding.ListItemProgressBinding
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem
import com.senyk.rickandmorty.presentation.presentation.entity.ProgressListItem
import com.senyk.rickandmorty.presentation.presentation.recycler.viewholder.BaseDataBindingViewHolder

class ProgressAdapterDelegate : AdapterDelegate<List<ListItem>>() {

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean =
        items[position] is ProgressListItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemProgressBinding.inflate(inflater, parent, false)
        return BaseDataBindingViewHolder<ListItemProgressBinding>(binding.root)
    }

    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        // No binding required
    }
}
