package com.makeus.daycarat.presentation.fragment.login

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinAdvantageBinding
import com.makeus.daycarat.presentation.login.JoinActivity
import com.makeus.daycarat.presentation.login.JoinFragment
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.UiManager


class AdvantageFragment() : BaseFragment<FragmentJoinAdvantageBinding>(
    FragmentJoinAdvantageBinding::inflate
) {

    private val viewModel by activityViewModels<UserDataViewmodel>()

    override fun initView() {
        var array = resources.getStringArray(R.array.advantage_datas)
        array.forEachIndexed { index, s ->
            binding.chipGroup.addView(createTagChip(s, index))
        }
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                checkedIds.getOrNull(0)?.let { pos ->
                    array.getOrNull(pos)?.let { text ->
                        Log.d(Constant.TAG, "text $text")
                        (parentFragment as JoinFragment).enableNextBtn(true)
                        viewModel.userData.value.strength = text
                    }
                }
            } else {
                (activity as JoinActivity).enableNextBtn(false)
            }


        }
    }

    override fun initStatusBar() {

    }

    override fun onResume() {
        super.onResume()
        if (binding.chipGroup.checkedChipId != -1) (parentFragment as JoinFragment).enableNextBtn(true)


    }

    private fun createTagChip(statetext: String, pos: Int): Chip {
        return Chip(context).apply {
            setChipBackgroundColorResource(R.color.chip_background_color)
            chipStrokeColor = resources.getColorStateList(R.color.chip_stroke_color, null)
            chipStrokeWidth = UiManager.getPixel(1).toFloat()

            setTextColor(resources.getColorStateList(R.color.chip_text_color, null))
//            setBackgroundResource(R.drawable.ic_chip)
            isCheckedIconVisible = false
            text = statetext
            isCheckable = true
            isClickable = true
            tag = statetext
            id = pos
//            chipStrokeWidth= 1F
//            setChipStrokeColorResource(R.color.Gray)
        }

    }

}