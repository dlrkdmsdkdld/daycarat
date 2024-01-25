package com.makeus.daycarat.presentation.fragment.episode

import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.databinding.FragmentGemBinding
import com.makeus.daycarat.databinding.FragmentSoaraBinding
import com.makeus.daycarat.databinding.LayoutEpisodeDetailContentBinding
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeSeeViewModel
import com.makeus.daycarat.presentation.viewmodel.episode.SoaraViewmodel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.UiManager.inflateDetailContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SoaraFragment() : BaseFragment<FragmentSoaraBinding>(
    FragmentSoaraBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(SoaraViewmodel::class.java)
    }

    companion object{
        const val IS_USER_LAST_FOLD_EPISODE = "IS_USER_LAST_FOLD_EPISODE"
    }

    override fun initView() {
        val args: SoaraFragmentArgs by navArgs()
        viewModel.setEpisode(args.episodeContent)

        repeatOnStarted {
            viewModel.episodeConetent.collect {
                binding.textTitle.text = it.title
                binding.textDate.text = it.selectedDate
                binding.fieldAddPoint.removeAllViews()
                it.episodeContents.forEach {
                    binding.fieldAddPoint.addView(inflateDetailContent(it , layoutInflater))
                }
            }
        }


        binding.fieldAddPoint.isVisible = SharedPreferenceManager.getInstance().getBoolean(IS_USER_LAST_FOLD_EPISODE , true)
        binding.btnFoleEpiosde.onThrottleClick {
            var now = SharedPreferenceManager.getInstance().getBoolean(IS_USER_LAST_FOLD_EPISODE , true)
            SharedPreferenceManager.getInstance().setBoolean(IS_USER_LAST_FOLD_EPISODE , !now)
            binding.fieldAddPoint.isVisible = !now
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