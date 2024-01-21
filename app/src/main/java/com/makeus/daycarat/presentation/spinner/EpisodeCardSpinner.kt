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
import com.makeus.daycarat.presentation.viewmodel.EditEpisodeViewmodel


class EpisodeCardSpinner(context: Context, var list: List<String> )
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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerEpisodeBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)
        binding.textSubmit.text = data

        return binding.root
    }

    override fun isEnabled(position: Int): Boolean {
//        return viewmodel.epiosdeContentTypeListPos.indexOf(position) == -1
        return super.isEnabled(position)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? { // 드롭다운되었을
        val binding = ItemSpinnerEpisodeDropdownBinding.inflate(LayoutInflater.from(parent?.context),parent,false)
        var data = list.getOrNull(position)
        binding.textSubmit.text = data


        return binding.root
//        return super.getDropDownView(position, convertView, parent)
    }

}
