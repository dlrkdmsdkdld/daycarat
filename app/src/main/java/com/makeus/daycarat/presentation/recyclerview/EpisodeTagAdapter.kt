package com.makeus.daycarat.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.DayCaratApplication
import com.makeus.daycarat.R
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.ItemEpisodeCardBinding
import com.makeus.daycarat.databinding.LayoutSearchItemBinding


class EpisodeTagAdapter(var list: List<EpisodeActivityCounter> ,
                        var viewType : EpisodeTagViewType ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onclickActivity: ((String) -> Unit)? = null
    var onclickDate: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEpisodeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(list[position] , viewType)
    }

    fun changeType(newlist:List<EpisodeActivityCounter> , changeType : EpisodeTagViewType){
        viewType = changeType
        list = newlist
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemEpisodeCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:EpisodeActivityCounter , type :EpisodeTagViewType ){
            if (type == EpisodeTagViewType.Activity){
                binding.root.setOnClickListener {
                    onclickActivity?.invoke(data.activityTagName)
                }
                binding.textTitle.text = data.activityTagName
            }else {
                binding.root.setOnClickListener {
                    onclickDate?.invoke(data.month)
                }
                binding.textTitle.text = "${data.month}월"
            }
            binding.textCount.text = data.quantity.toString()


        }

    }



}
enum class EpisodeTagViewType{
    Activity,Date
}