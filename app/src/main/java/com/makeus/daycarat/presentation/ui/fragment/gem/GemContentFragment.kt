package com.makeus.daycarat.presentation.fragment.gem

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.gem.GemContentViewModel
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import com.makeus.daycarat.presentation.util.UiEvent
import com.makeus.daycarat.presentation.util.UiManager
import com.makeus.daycarat.presentation.util.UiManager.setGemCardBgColor
import com.makeus.daycarat.presentation.util.UiManager.setGemDes
import com.makeus.daycarat.presentation.util.UiManager.setGemImage
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
        viewmodel.inputEpsodeId(args.episodeId, args.keyword)

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
            viewmodel.flowAIKeywordEvent.collectLatest { event ->
                binding.fieldAiSentence.visibility = View.VISIBLE
                when (event) {
                    is UiEvent.ServerFailEvent -> {
                        binding.fieldYesSentence.visibility = View.GONE
                        binding.fieldNoSentence.visibility = View.VISIBLE
                        binding.fieldWorkSentence.visibility = View.GONE

                        binding.fieldYesKeyword.visibility = View.GONE
                        binding.fieldNoKeyword.visibility = View.VISIBLE
                        binding.fieldWorkKeyword.visibility = View.GONE
                    }
                    is UiEvent.WorkingEvent -> {
                        binding.fieldYesSentence.visibility = View.GONE
                        binding.fieldNoSentence.visibility = View.GONE
                        binding.fieldWorkSentence.visibility = View.VISIBLE

                        binding.fieldYesKeyword.visibility = View.GONE
                        binding.fieldNoKeyword.visibility = View.GONE
                        binding.fieldWorkKeyword.visibility = View.VISIBLE
                    }
                    is UiEvent.SuccessEvent -> {
                        binding.fieldYesSentence.visibility = View.VISIBLE
                        binding.fieldNoSentence.visibility = View.GONE
                        binding.fieldWorkSentence.visibility = View.GONE

                        binding.fieldYesKeyword.visibility = View.VISIBLE
                        binding.fieldNoKeyword.visibility = View.GONE
                        binding.fieldWorkKeyword.visibility = View.GONE
                    }

                    else -> {

                    }
                }

            }
        }
        repeatOnStarted {
            viewmodel.AISoara.collectLatest {
                binding.textAi1.text = it.generatedContent1
                binding.textAi2.text = it.generatedContent2
                binding.textAi3.text = it.generatedContent3
            }
        }

        repeatOnStarted {
            viewmodel.flowCopyEvent.collectLatest {
                when(it){
                    is UiEvent.CopyEvent ->{
//                        Toast.makeText(requireContext(), "복사되었습니다" , Toast.LENGTH_SHORT).show() // 알아서 토스트메시지뜸
                        val clipboard: ClipboardManager = requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("label", it.copyData)
                        clipboard.setPrimaryClip(clip)
                    }else ->{}
                }
            }
        }

        setKeyword()
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

    fun setKeyword() {
        setGemImage(viewmodel.keyword, binding.imageGem)
        setGemDes(viewmodel.keyword, binding.textGemDes)
        setGemCardBgColor(viewmodel.keyword,binding.cardGem)

        binding.textGemTitle.text = viewmodel.keyword
    }

    fun initListener() {
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.btnKeywordFail.onThrottleClick {
            clickKeyword()
        }
        binding.btnKeyword.onThrottleClick {
            clickKeyword()
        }
        binding.btnCopy.onThrottleClick {
            Toast.makeText(requireContext(), "복사시작" , Toast.LENGTH_SHORT).show()
            viewmodel.getCopyString()
        }
    }
    fun clickKeyword(){
        findNavController().navigate(
            R.id.action_gemContentFragment_to_gemKeywordFragment,
            bundleOf("episode_id" to viewmodel.episodeId, "keyword" to viewmodel.keyword)
        )
    }

}