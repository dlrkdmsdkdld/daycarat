package com.makeus.daycarat.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeDropdownBinding

class SearchTagAdapter(val list: ArrayList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>()  , Filterable {
    var onclick:((String)->Unit)? = null

    private var parselist: ArrayList<String>? = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemSpinnerEpisodeDropdownBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding , onclick)
    }

    override fun getItemCount(): Int {
        return parselist?.size!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        parselist?.getOrNull(position)?.let { (holder as ViewHolder).bind(it) }
    }


    inner class ViewHolder(val binding: ItemSpinnerEpisodeDropdownBinding,val onclick:((String)->Unit)?) : RecyclerView.ViewHolder(binding.root) {
        fun bind(text:String){
            binding.textSubmit.text = text
            binding.root.setOnClickListener {
                onclick?.invoke(text)
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val charString = constraint.toString()
                parselist = if (charString.isEmpty()) {
                    list
                } else {
                    val filteredList = ArrayList<String>()
                    if (list != null) {
                        for (name in list) {
                            if(name.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(name);
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = parselist
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                parselist  = results.values as ArrayList<String>
                notifyDataSetChanged()
            }
        }
    }

}
