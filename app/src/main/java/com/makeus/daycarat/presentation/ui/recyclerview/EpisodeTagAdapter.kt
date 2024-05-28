package com.makeus.daycarat.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.ItemEpisodeCardBinding
import com.makeus.daycarat.presentation.util.EpisodeTagViewType


class EpisodeTagAdapter(var viewType : EpisodeTagViewType ,
                        var onClick: ((EpisodeActivityCounter) -> Unit)) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val differCallback = object : DiffUtil.ItemCallback<EpisodeActivityCounter>() {
        override fun areItemsTheSame(oldItem: EpisodeActivityCounter, newItem: EpisodeActivityCounter): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: EpisodeActivityCounter, newItem: EpisodeActivityCounter): Boolean {
            return oldItem == newItem
        }
    }
    val asyncListDiffer = AsyncListDiffer(this,differCallback)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEpisodeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeTagViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        asyncListDiffer.currentList.getOrNull(position)?.let{ data ->
            (holder as EpisodeTagViewHolder).bind(data , viewType)
            holder.binding.root.setOnClickListener {
                onClick.invoke(data )
            }
        }

    }

    fun changeType(newlist:List<EpisodeActivityCounter>, changeType : EpisodeTagViewType){
        viewType = changeType
        asyncListDiffer.submitList(newlist)
    }
}
class EpisodeTagViewHolder(val binding: ItemEpisodeCardBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: EpisodeActivityCounter, type : EpisodeTagViewType){
        if (type == EpisodeTagViewType.Activity){
            binding.textTitle.text = data.activityTagName
        }else {
            binding.textTitle.text = "${data.month}월"
        }
        binding.textCount.text = data.quantity.toString()
    }

}
