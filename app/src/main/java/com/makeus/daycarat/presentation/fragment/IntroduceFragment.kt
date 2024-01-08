package com.makeus.daycarat.presentation.fragment

import android.os.Bundle
import android.view.View
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentIntroduce1Binding


class IntroduceFragment(val type:Int) : BaseFragment<FragmentIntroduce1Binding>(FragmentIntroduce1Binding::inflate) {


    override fun initView() {
        when(type){
            IntroduceType.STATE_FIRST.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce1)
                binding.nextBtn.visibility = View.INVISIBLE

            }
            IntroduceType.STATE_SECOND.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce2)
                binding.nextBtn.visibility = View.INVISIBLE

            }
            IntroduceType.STATE_THIRD.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce3)
                binding.nextBtn.visibility = View.VISIBLE
            }
        }
    }

}
enum class IntroduceType{
    STATE_FIRST, STATE_SECOND, STATE_THIRD
}
