package com.makeus.daycarat.presentation.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentIntroduce1Binding
import com.makeus.daycarat.databinding.FragmentJoinNicknameBinding

class NicknameFragment() : BaseFragment<FragmentJoinNicknameBinding>(
    FragmentJoinNicknameBinding::inflate) {


    override fun initView() {
        binding.edit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

}
