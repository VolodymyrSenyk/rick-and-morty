package com.senyk.rickandmorty.presentation.presentation.recycler.adapterdelegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.senyk.rickandmorty.presentation.databinding.ListItemEmptyStateBinding
import com.senyk.rickandmorty.presentation.presentation.entity.EmptyStateListItem
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem
import com.senyk.rickandmorty.presentation.presentation.recycler.viewholder.BaseDataBindingViewHolder

class EmptyStateAdapterDelegate : AdapterDelegate<List<ListItem>>() {

    override fun isForViewType(items: List<ListItem>, position: Int): Boolean =
        items[position] is EmptyStateListItem

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemEmptyStateBinding.inflate(inflater, parent, false)
        return BaseDataBindingViewHolder<ListItemEmptyStateBinding>(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(
        items: List<ListItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val viewHolder = holder as? BaseDataBindingViewHolder<ListItemEmptyStateBinding> ?: return
        val model = items[position] as? EmptyStateListItem
            ?: return
        viewHolder.binding?.apply {
            this.model = model
        }
    }
}
