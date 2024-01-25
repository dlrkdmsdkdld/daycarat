package com.makeus.daycarat.presentation.fragment.episode

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.databinding.FragmentEpisodeSeeContentBinding
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeSeeViewModel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.UiManager.inflateDetailContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EpisodeSeeContentFragment() : BaseFragment<FragmentEpisodeSeeContentBinding>(
    FragmentEpisodeSeeContentBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeSeeViewModel::class.java)
    }

    override fun initView() {
        val arg: EpisodeSeeContentFragmentArgs by navArgs()
        viewModel.getEpisode(arg.episodeId)


        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.btnAddEdit.onThrottleClick {
            findNavController().navigate(R.id.action_episodeSeeContentFragment_to_soaraFragment , bundleOf("episodeContent" to viewModel.getEpisodeContent() )  )
        }
        repeatOnStarted {
            viewModel.episodeConetent.collectLatest {
                binding.textTitle.text = it.title
                binding.textDate.text = it.selectedDate
                binding.textTag.text = it.activityTagName
                binding.fieldAddPoint.removeAllViews()
                it.episodeContents.forEach {
                    binding.fieldAddPoint.addView(inflateDetailContent(it , layoutInflater))
                }

            }
        }

    }

    override fun onResume() {
        super.onResume()

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