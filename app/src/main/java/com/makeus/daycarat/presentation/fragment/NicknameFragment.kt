package com.makeus.daycarat.presentation.fragment

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.activityViewModels
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentIntroduce1Binding
import com.makeus.daycarat.databinding.FragmentJoinNicknameBinding
import com.makeus.daycarat.presentation.login.JoinActivity
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel

class NicknameFragment() : BaseFragment<FragmentJoinNicknameBinding>(
    FragmentJoinNicknameBinding::inflate) {
    private val viewModel by activityViewModels<UserDataViewmodel>()


    override fun initView() {
        binding.edit.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                viewModel.userData.value.nickname = p0.toString()
                binding.textCount.text = "${p0?.length?:0}/10"
                (activity as JoinActivity).enableNextBtn(p0.toString().isNotEmpty())
            }

        })
    }

}
