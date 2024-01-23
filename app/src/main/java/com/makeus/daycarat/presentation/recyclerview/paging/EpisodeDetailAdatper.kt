package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.R
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.databinding.ItemEpisodeCardBinding
import com.makeus.daycarat.databinding.ItemEpisodeDetailBinding
import com.makeus.daycarat.databinding.ItemPagingLoadingBinding
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagAdapter
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagViewType

class EpisodeDetailAdatper () :
    PagingDataAdapter<EpisodeDetailContent, RecyclerView.ViewHolder>(object :DiffUtil.ItemCallback<EpisodeDetailContent>(){
        override fun areItemsTheSame(oldItem: EpisodeDetailContent, newItem: EpisodeDetailContent): Boolean {
            //같은 객체인지 체크합니다
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EpisodeDetailContent, newItem: EpisodeDetailContent): Boolean {
            //같은 내용물인지 check 합니다
            return oldItem.title == newItem.title
        }
    }){
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {(holder as ViewHolder).bind(it)  }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemEpisodeDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    inner class ViewHolder(val binding: ItemEpisodeDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EpisodeDetailContent){
            binding.textTitle.text = data.title
            binding.textDate.text = data.date
            binding.textDes.text = data.content
//            binding.textKeyword = data.episodeState
        }

    }


}
