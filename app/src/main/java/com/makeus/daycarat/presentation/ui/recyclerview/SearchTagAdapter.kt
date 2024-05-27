package com.makeus.daycarat.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeDropdownBinding
import com.makeus.daycarat.databinding.LayoutSearchItemBinding

class SearchTagAdapter(var list:List<String>? , var onclick:((String)->Unit)): RecyclerView.Adapter<RecyclerView.ViewHolder>()  , Filterable {

    val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
    val asyncListDiffer = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding = LayoutSearchItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchTagViewHolder(binding , onclick)
    }

    override fun getItemCount(): Int {
        return asyncListDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        asyncListDiffer.currentList?.getOrNull(position)?.let { (holder as SearchTagViewHolder).bind(it) }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                var parselist = if (charString.isEmpty()) {
                    list
                } else {
                    val filteredList = ArrayList<String>()
                    list?.forEach {
                        if(it.toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(it);
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = parselist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                asyncListDiffer.submitList(results.values as List<String>)
            }
        }
    }
}
class SearchTagViewHolder(val binding: LayoutSearchItemBinding,val onclick:((String)->Unit)?) : RecyclerView.ViewHolder(binding.root) {
    fun bind(text:String){
        binding.textSubmit.text = text
        binding.root.setOnClickListener {
            onclick?.invoke(text)
        }
    }

}
