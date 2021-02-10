package com.senyk.rickandmorty.presentation.presentation.recycler.viewholder

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

open class BaseDataBindingViewHolder<T>(view: View) : RecyclerView.ViewHolder(view) {
    val binding: T? = DataBindingUtil.bind(view)
}
