package com.makeus.daycarat.presentation.fragment

import android.content.Context
import com.google.android.material.chip.Chip
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinAdvantageBinding
import com.makeus.daycarat.databinding.FragmentJoinJobBinding


class AdvantageFragment() : BaseFragment<FragmentJoinAdvantageBinding>(
    FragmentJoinAdvantageBinding::inflate) {


    override fun initView() {

    }

    private fun createTagChip(context: Context, statetext: String, idData:Int): Chip {
        return Chip(context).apply {
            setChipBackgroundColorResource(R.color.chip_background_color)
            setTextColor(resources.getColorStateList(R.color.chip_text_color))
//            setBackgroundResource(R.drawable.ic_chip)
            isCheckedIconVisible=false
            text  = statetext
            isCheckable=true
            isClickable=true
            tag = statetext
            id = idData
//            chipStrokeWidth= 1F
//            setChipStrokeColorResource(R.color.Gray)
        }

    }

}