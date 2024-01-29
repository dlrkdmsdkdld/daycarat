package com.makeus.daycarat.presentation.fragment.login

import android.content.Intent
import android.view.View
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentIntroduce1Binding
import com.makeus.daycarat.presentation.login.JoinActivity


class IntroduceFragment(val type:Int) : BaseFragment<FragmentIntroduce1Binding>(FragmentIntroduce1Binding::inflate) {

    override fun initView() {
        when(type){
            IntroduceType.STATE_FIRST.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce1)
                binding.nextBtn.visibility = View.INVISIBLE
                binding.imgIntroduce.setImageResource(R.drawable.introduce_1)
                binding.nextBtn.isEnabled = false


            }
            IntroduceType.STATE_SECOND.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce2)
                binding.nextBtn.visibility = View.INVISIBLE
                binding.imgIntroduce.setImageResource(R.drawable.introduce_2)
                binding.nextBtn.isEnabled = false

            }
            IntroduceType.STATE_THIRD.ordinal ->{
                binding.textTitle.text = resources.getText(R.string.title_introduce3)
                binding.imgIntroduce.setImageResource(R.drawable.introduce_3)
                binding.nextBtn.visibility = View.VISIBLE
                binding.nextBtn.isEnabled = true
            }
        }
        binding.nextBtn.setOnClickListener {
            Intent(activity, JoinActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    override fun initStatusBar() {
    }

}
enum class IntroduceType{
    STATE_FIRST, STATE_SECOND, STATE_THIRD
}
