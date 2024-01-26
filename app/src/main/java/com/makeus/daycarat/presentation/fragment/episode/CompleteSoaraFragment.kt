package com.makeus.daycarat.presentation.fragment.episode

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentCompleteSoaraBinding
import com.makeus.daycarat.databinding.FragmentEditSoaraBinding
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.statusBarHeight


class CompleteSoaraFragment() : BaseFragment<FragmentCompleteSoaraBinding>(
    FragmentCompleteSoaraBinding::inflate) {

    override fun initView() {
        binding.nextBtn.onThrottleClick {
            findNavController().navigate(R.id.action_completeSoaraFragment_to_episodeDetailTypeFragment)
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