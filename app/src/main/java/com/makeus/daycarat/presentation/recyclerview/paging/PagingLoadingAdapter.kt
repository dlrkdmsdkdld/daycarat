package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

// 로딩 상태가 LoadState.Loading 일 때 로딩 스피너를 보여주고,
// LoadingState.Error 일 때는 에러메시지, 재시작 버튼을 보여주는 어댑터다.
class PagingLoadingAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent, retry)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}