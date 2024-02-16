package com.makeus.daycarat.presentation.fragment.episode

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentCompleteSoaraBinding
import com.makeus.daycarat.databinding.FragmentEditSoaraBinding
import com.makeus.daycarat.presentation.viewmodel.episode.CompleteSoaraViewmodel
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeDetailTypeViewModel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompleteSoaraFragment() : BaseFragment<FragmentCompleteSoaraBinding>(
    FragmentCompleteSoaraBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(CompleteSoaraViewmodel::class.java)
    }

    override fun initView() {
        binding.nextBtn.onThrottleClick {
            var result = findNavController().popBackStack(R.id.episodeDetailTypeFragment , false)
            if (!result) findNavController().navigate(R.id.action_completeSoaraFragment_to_homeFragment)
        }
        val args:CompleteSoaraFragmentArgs by navArgs()
        viewModel.register(args.episodeId)
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