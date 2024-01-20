package com.makeus.daycarat.presentation.fragment

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentJoinNicknameBinding
import com.makeus.daycarat.databinding.FragmentWellcomeBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.login.JoinActivity
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel


class WellcomeFragment() : BaseFragment<FragmentWellcomeBinding>(
    FragmentWellcomeBinding::inflate) {

    override fun initView() {
        binding.nextBtn.setOnClickListener {
            Intent(activity,MainActivity::class.java).apply {
                activity?.finishAffinity()
                startActivity(this)
            }
        }

    }

    override fun initStatusBar() {

    }

}