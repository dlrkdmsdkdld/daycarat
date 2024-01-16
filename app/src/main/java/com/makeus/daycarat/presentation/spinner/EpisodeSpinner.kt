package com.makeus.daycarat.presentation.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.makeus.daycarat.R
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeBinding
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeDropdownBinding


class EpisodeSpinner(context: Context, var list: List<String>, var select:Int )
    : ArrayAdapter<String>(context, R.layout.item_spinner_episode, list) {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
    fun changeSelection(pos:Int){
        select = pos
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerEpisodeBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)

        if (data.isNullOrEmpty()){
            binding.textSubmit.text = context.getString(R.string.episode_item_hint)
            binding.textSubmit.setTextColor( context.getColor(R.color.gray_scale_400))
        }else{
            binding.textSubmit.text = data
            binding.textSubmit.setTextColor( context.getColor(R.color.gray_scale_900))
        }

//        binding.imgSpinner.visibility = View.GONE
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? { // 드롭다운되었을
        val binding = ItemSpinnerEpisodeDropdownBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)
        binding.textSubmit.text = data
//
//        if(RecommendState.HEADER ==  data.state){
//            binding.textHeader.visibility = View.VISIBLE
//            binding.textHeader.text = data.subject.subject_name
//        }else{
//            binding.textHeader.visibility = View.GONE
//        }
//        binding.textSpinner.text = " ${data.categories.category.category_name} (${data.categories.contentCount})"
////        if (data.categories.recommandUser.isRunning) binding.imgSpinner.visibility = View.VISIBLE
////        else binding.imgSpinner.visibility = View.GONE
//
//        if (select == position){
//            binding.imgSpinner.setColorFilter(ThemeManager.getAppbar())
//            binding.textSpinner.setTextColor(ThemeManager.getAppbar())
//            binding.imgSpinner.visibility = View.VISIBLE
//        }else{
//            binding.textSpinner.setTextColor(context.getColor(R.color.bg_content_dark))
//            binding.imgSpinner.visibility = View.GONE
//        }
//
//
//
        return binding.root
//        return super.getDropDownView(position, convertView, parent)
    }

}

