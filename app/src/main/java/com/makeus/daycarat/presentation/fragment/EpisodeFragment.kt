package com.makeus.daycarat.presentation.fragment

import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.FragmentEpisodeBinding
import com.makeus.daycarat.databinding.FragmentIntroduce1Binding
import com.makeus.daycarat.presentation.login.JoinActivity
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagAdapter
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagViewType
import com.makeus.daycarat.presentation.viewmodel.EditEpisodeViewmodel
import com.makeus.daycarat.presentation.viewmodel.EpisodeViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeFragment() : BaseFragment<FragmentEpisodeBinding>(
    FragmentEpisodeBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeViewmodel::class.java)
    }
    private val episodeAdapter by lazy {
        EpisodeTagAdapter(listOf<EpisodeActivityCounter>() , EpisodeTagViewType.Activity)
    }

    override fun initView() {
        binding.recyclerEpisode.adapter = episodeAdapter


        repeatOnStarted {
            viewModel.episodeCountList.collectLatest {
                if (binding.chipActivity.isChecked) episodeAdapter.changeType(it , EpisodeTagViewType.Activity)
                else episodeAdapter.changeType(it , EpisodeTagViewType.Date)
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