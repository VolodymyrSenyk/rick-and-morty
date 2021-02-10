package com.senyk.rickandmorty.presentation.presentation.recycler.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.senyk.rickandmorty.presentation.presentation.entity.ListItem

class BaseDataBindingDelegationAdapter(
    delegatesList: List<AdapterDelegate<List<ListItem>>>
) : AsyncListDifferDelegationAdapter<ListItem>(ListDiffCallback()) {

    init {
        delegatesList.forEach { delegate ->
            delegatesManager.addDelegate(delegate)
        }
    }

    override fun getItemViewType(position: Int): Int =
        delegatesManager.getItemViewType(items, position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        delegatesManager.onBindViewHolder(items, position, holder)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: List<*>
    ) = delegatesManager.onBindViewHolder(items, position, holder, payloads)

    override fun getItemCount(): Int = items.size

    private class ListDiffCallback<T : ListItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.listId == newItem.listId

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem.equals(newItem)
    }
}
