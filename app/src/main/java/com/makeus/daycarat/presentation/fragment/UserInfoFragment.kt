package com.makeus.daycarat.presentation.fragment

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentGemBinding
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import kotlinx.coroutines.flow.collectLatest

class UserInfoFragment() : BaseFragment<FragmentUserInfoBinding>(
    FragmentUserInfoBinding::inflate) {
    private val mainViewModel: MainViewmodel by activityViewModels()

    override fun initView() {

        repeatOnStarted {
            mainViewModel.userData.collect {
                binding.textEmail.text = it.email
                binding.textNickname.text = it.nickname
                Log.d("profileImage","profileImage ${it.profileImage}")
                Glide.with(this@UserInfoFragment)
                    .load(it.profileImage)
                    .error(R.drawable.bg_home)
                    .into(binding.imgProfile)


            }
        }


    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }

}