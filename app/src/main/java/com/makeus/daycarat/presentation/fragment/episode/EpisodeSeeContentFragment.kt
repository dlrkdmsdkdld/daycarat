package com.makeus.daycarat.presentation.fragment.episode

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.databinding.FragmentEpisodeSeeContentBinding
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding
import com.makeus.daycarat.presentation.viewmodel.EpisodeDetailTypeViewModel
import com.makeus.daycarat.presentation.viewmodel.EpisodeSeeViewModel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
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

        repeatOnStarted {
            viewModel.episode.collectLatest {
                binding.textTitle.text = it.title
                binding.textDate.text = it.selectedDate
                binding.textTag.text = it.activityTagName
                it.episodeContents.forEach {
                    binding.fieldAddPoint.addView(inflateDetailContent(it))
                }

            }
        }

        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
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

    fun inflateDetailContent(data: EpisodeContent): ConstraintLayout {
        var inflating = LayoutEpisodeDetailContentBinding.inflate(layoutInflater)
        inflating.textTitle.text = data.episodeContentType
        inflating.textDes.text = data.content
        return inflating.root

    }

}