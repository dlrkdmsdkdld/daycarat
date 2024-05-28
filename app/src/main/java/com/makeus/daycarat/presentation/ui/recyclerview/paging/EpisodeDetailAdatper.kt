package com.makeus.daycarat.presentation.recyclerview.paging

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.data.data.EpisodeDetailContent
import com.makeus.daycarat.databinding.ItemEpisodeDetailBinding
import com.makeus.daycarat.presentation.util.Constant
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick

class EpisodeDetailAdatper(var onclick: ((Int) -> Unit)) :
    PagingDataAdapter<EpisodeDetailContent, RecyclerView.ViewHolder>(object :
        DiffUtil.ItemCallback<EpisodeDetailContent>() {
        override fun areItemsTheSame(
            oldItem: EpisodeDetailContent,
            newItem: EpisodeDetailContent
        ): Boolean {
            //같은 객체인지 체크합니다
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: EpisodeDetailContent,
            newItem: EpisodeDetailContent
        ): Boolean {
            //같은 내용물인지 check 합니다
            return oldItem.title == newItem.title
        }
    }) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { item ->
            (holder as EpisodeDetailViewHolder).bind(item)
            (holder as EpisodeDetailViewHolder).binding.root.onThrottleClick {
                onclick.invoke(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EpisodeDetailViewHolder(
            ItemEpisodeDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}

class EpisodeDetailViewHolder(val binding: ItemEpisodeDetailBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(data: EpisodeDetailContent) {
        binding.textTitle.text = data.title
        binding.textDate.text = data.date
        binding.textDes.text = data.content
        if (data.episodeState.equals(Constant.NO_SOARA)) {
            binding.textKeyword.visibility = View.GONE
            binding.imgDiamond.visibility = View.GONE
        } else {
            data.episodeKeyword?.let {
                binding.textKeyword.text = it
            } ?: kotlin.run {
                binding.textKeyword.visibility = View.INVISIBLE // Invisible로 하셈 키워드로 다이아 위치조정해서그럼
//                    binding.textKeyword.text = ""
            }
            binding.textKeyword.visibility = View.VISIBLE
            binding.imgDiamond.visibility = View.VISIBLE
        }
//            binding.textKeyword = data.episodeState
    }

}
