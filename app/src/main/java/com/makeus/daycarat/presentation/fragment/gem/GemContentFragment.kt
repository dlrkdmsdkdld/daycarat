package com.makeus.daycarat.presentation.fragment.gem

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.presentation.viewmodel.gem.GemContentViewModel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.UiManager
import com.makeus.daycarat.util.UiManager.setGemDes
import com.makeus.daycarat.util.UiManager.setGemImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GemContentFragment() : BaseFragment<FragmentGemContentBinding>(
    FragmentGemContentBinding::inflate
) {
    val args: GemContentFragmentArgs by navArgs()

    private val viewmodel by lazy {
        ViewModelProvider(this).get(GemContentViewModel::class.java)
    }

    override fun initView() {
        viewmodel.inputEpsodeId(args.episodeId , args.keyword)

        repeatOnStarted {
            viewmodel.episodeSoara.collectLatest {
                binding.textSituation.text = it.content1
                binding.textObjective.text = it.content2
                binding.textAction.text = it.content3
                binding.textResult.text = it.content4
                binding.textAftermath.text = it.content5

            }
        }

        repeatOnStarted {
            viewmodel.episodeConetent.collectLatest {
                binding.textTitle.text = it.title
                binding.textDate.text = it.selectedDate
                binding.textTag.text = it.activityTagName
                binding.fieldAddEpisode.removeAllViews()
                it.episodeContents.forEach {
                    binding.fieldAddEpisode.addView(
                        UiManager.inflateDetailContent(
                            it,
                            layoutInflater
                        )
                    )
                }

            }
        }


        repeatOnStarted {
            viewmodel.flowAIKeywordEvent.collectLatest {

            }
        }
        setKeyword()


    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }

    fun setKeyword(){
        setGemImage(viewmodel.keyword , binding.imageGem)
        setGemDes(viewmodel.keyword , binding.textGemDes)
        binding.textGemTitle.text = viewmodel.keyword


    }

}