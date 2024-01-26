package com.makeus.daycarat.presentation.recyclerview.paging

import android.view.LayoutInflater
import android.view.View
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
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.onThrottleClick

class EpisodeDetailAdatper() :
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

    var onclick: ((Int) -> Unit)? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { (holder as ViewHolder).bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            ItemEpisodeDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class ViewHolder(val binding: ItemEpisodeDetailBinding) :
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
            binding.root.onThrottleClick {
                onclick?.invoke(data.id)
            }
//            binding.textKeyword = data.episodeState
        }

    }


}
