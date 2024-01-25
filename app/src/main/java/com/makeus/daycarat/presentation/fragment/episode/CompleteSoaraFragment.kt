package com.makeus.daycarat.presentation.fragment.episode

import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentCompleteSoaraBinding
import com.makeus.daycarat.databinding.FragmentEditSoaraBinding
import com.makeus.daycarat.util.Extensions.statusBarHeight


class CompleteSoaraFragment() : BaseFragment<FragmentCompleteSoaraBinding>(
    FragmentCompleteSoaraBinding::inflate) {

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