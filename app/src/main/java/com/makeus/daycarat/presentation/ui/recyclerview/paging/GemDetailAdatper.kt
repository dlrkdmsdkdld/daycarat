package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.data.data.GemDetailConetent
import com.makeus.daycarat.databinding.ItemEpisodeDetailBinding
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick

class GemDetailAdatper(val keyword: String , var onclick: ((Int) -> Unit)) :
    PagingDataAdapter<GemDetailConetent, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<GemDetailConetent>() {
        override fun areItemsTheSame(
            oldItem: GemDetailConetent,
            newItem: GemDetailConetent
        ): Boolean {
            //같은 객체인지 체크합니다
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GemDetailConetent,
            newItem: GemDetailConetent
        ): Boolean {
            //같은 내용물인지 check 합니다
            return oldItem.title == newItem.title
        }
    }) {



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { data->
            (holder as GemDetailViewHolder).bind(data ,keyword)
            (holder as GemDetailViewHolder).binding.root.onThrottleClick {
                onclick.invoke(data.episodeId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GemDetailViewHolder(
            ItemEpisodeDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}

class GemDetailViewHolder(val binding: ItemEpisodeDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: GemDetailConetent , keyword: String) {
        binding.textTitle.text = data.title
        binding.textDate.text = data.date
        binding.textDes.text = data.content

        binding.textKeyword.visibility = View.VISIBLE
        binding.textKeyword.text = keyword
        binding.imgDiamond.visibility = View.VISIBLE

//            binding.textKeyword = data.episodeState
    }
}