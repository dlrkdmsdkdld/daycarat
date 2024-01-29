package com.makeus.daycarat.presentation.fragment.gem

import androidx.compose.runtime.key
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeKeywordAndId
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.databinding.FragmentSelectKeywordBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.gem.GemDetailViewModel
import com.makeus.daycarat.presentation.viewmodel.gem.GemKeywordViewModel
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class GemKeywordFragment() : BaseFragment<FragmentSelectKeywordBinding>(
    FragmentSelectKeywordBinding::inflate
) {

    //    private val viewModel by lazy {
//        ViewModelProvider(this).get(GemKeywordViewModel::class.java)
//    }
    val args: GemKeywordFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: GemKeywordViewModel.GemKeywordViewModelFactory

    private val viewModel by viewModels<GemKeywordViewModel> {
        GemKeywordViewModel.provideFactory(
            viewModelFactory,
            EpisodeKeywordAndId(args.keyword, args.episodeId)
        )
    }

    override fun initView() {
        initListener()
        initSelectKeyword()

        repeatOnStarted {
            viewModel.userSelectKeyword.collectLatest {
                checkKeyword(it.keyword)
            }
        }

        repeatOnStarted {
            viewModel.flowEvent.collectLatest { event ->
                when (event) {
                    is AuthViewmodel.UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }

                    is AuthViewmodel.UiEvent.SuccessUpdateKeywordEvent -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                        finishEvent(event.result)
                    }

                    else -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                        finishEvent(null)
                    }
                }
            }
        }


    }

    fun finishEvent(data: EpisodeKeywordAndId?) {
        data?.let {
            findNavController().navigate(
                R.id.action_gemKeywordFragment_to_gemContentFragment,
                bundleOf("keyword" to data.keyword, "episode_id" to data.episodeId)
            )
        } ?: kotlin.run { // 수정실패
            findNavController().navigate(
                R.id.action_gemKeywordFragment_to_gemContentFragment,
                bundleOf(
                    "keyword" to viewModel.originalData.keyword,
                    "episode_id" to viewModel.originalData.episodeId
                )
            )
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

    fun initListener() {
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.btnComplete.onThrottleClick {
            viewModel.completeKeyword()
        }
    }

    fun checkKeyword(keyword: String) {
        binding.fieldCommunication.isSelected = false
        binding.fieldResolve.isSelected = false
        binding.fieldCreative.isSelected = false
        binding.fieldChallenge.isSelected = false
        binding.fieldProfession.isSelected = false
        binding.fieldExcutive.isSelected = false

        when (keyword) {
            "커뮤니케이션" -> binding.fieldCommunication.isSelected = true
            "문제 해결" -> binding.fieldResolve.isSelected = true
            "창의성" -> binding.fieldCreative.isSelected = true
            "도전 정신" -> binding.fieldChallenge.isSelected = true
            "전문성" -> binding.fieldProfession.isSelected = true
            "실행력" -> binding.fieldExcutive.isSelected = true
        }
        if (keyword.isNotEmpty() && !keyword.equals("미선택")) binding.btnComplete.isEnabled = true
    }

    fun initSelectKeyword() {
        binding.fieldCommunication.setOnClickListener {
            viewModel.updateKeyword("커뮤니케이션")
        }
        binding.fieldResolve.setOnClickListener {
            viewModel.updateKeyword("문제 해결")
        }
        binding.fieldCreative.setOnClickListener {
            viewModel.updateKeyword("창의성")
        }
        binding.fieldChallenge.setOnClickListener {
            viewModel.updateKeyword("도전 정신")
        }
        binding.fieldProfession.setOnClickListener {
            viewModel.updateKeyword("전문성")
        }
        binding.fieldExcutive.setOnClickListener {
            viewModel.updateKeyword("실행력")
        }

    }

}