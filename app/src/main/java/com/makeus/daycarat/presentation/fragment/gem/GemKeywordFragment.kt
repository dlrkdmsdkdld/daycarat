package com.makeus.daycarat.presentation.fragment.gem

import androidx.navigation.fragment.findNavController
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.databinding.FragmentSelectKeywordBinding
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GemKeywordFragment() : BaseFragment<FragmentSelectKeywordBinding>(
    FragmentSelectKeywordBinding::inflate
) {
    override fun initView() {
        initListener()
    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }

    fun initListener(){
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
    }
}