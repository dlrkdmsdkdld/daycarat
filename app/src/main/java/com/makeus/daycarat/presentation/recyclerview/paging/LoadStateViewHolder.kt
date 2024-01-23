package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.R
import com.makeus.daycarat.databinding.ItemPagingLoadingBinding

class LoadStateViewHolder(
    parent: ViewGroup,
    retry: () -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.item_paging_loading, parent, false)
) {

    private val binding = ItemPagingLoadingBinding.bind(itemView)
    private val progressBar: ProgressBar = binding.progress
    private val errorMsg: TextView = binding.textError
    private val retry = binding.btnRetry
        .also {
            it.setOnClickListener { retry() }
        }

    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            errorMsg.text = loadState.error.localizedMessage
        }

        progressBar.isVisible = loadState is LoadState.Loading
//        progressBar.isVisible = true
        retry.isVisible = loadState is LoadState.Error
//        errorMsg.isVisible = loadState is LoadState.Error
    }
}

