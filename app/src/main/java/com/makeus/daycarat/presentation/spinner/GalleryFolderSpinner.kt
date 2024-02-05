package com.makeus.daycarat.presentation.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.makeus.daycarat.DayCaratApplication
import com.makeus.daycarat.R
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeBinding
import com.makeus.daycarat.databinding.ItemSpinnerEpisodeDropdownBinding
import com.makeus.daycarat.databinding.ItemSpinnerGalleryBinding
import com.makeus.daycarat.databinding.ItemSpinnerGalleryDropdownBinding
import com.makeus.daycarat.presentation.viewmodel.episode.EditEpisodeViewmodel


class GalleryFolderSpinnerdeSpinner(context: Context, var list: List<String>) :
    ArrayAdapter<String>(context, R.layout.item_spinner_episode, list) {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerGalleryBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)
        data?.split("/")?.last()?.let {
            binding.textSubmit.text = it
        }
        binding.textSubmit.setTextColor( context.getColor(R.color.gray_scale_900))

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? { // 드롭다운되었을
        val binding = ItemSpinnerGalleryDropdownBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)
        data?.split("/")?.last()?.let {
            binding.textSubmit.text = it
        }


        return binding.root
//        return super.getDropDownView(position, convertView, parent)
    }

}