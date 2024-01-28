package com.makeus.daycarat.presentation.fragment.gem

import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GemContentFragment() : BaseFragment<FragmentGemContentBinding>(
    FragmentGemContentBinding::inflate) {
    override fun initView() {
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