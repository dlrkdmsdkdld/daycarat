package com.makeus.daycarat.presentation.fragment.gem

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeKeywordAndId
import com.makeus.daycarat.databinding.FragmentGemContentBinding
import com.makeus.daycarat.databinding.FragmentSelectKeywordBinding
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
    val args :GemKeywordFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: GemKeywordViewModel.GemKeywordViewModelFactory

    private val viewModel by viewModels<GemKeywordViewModel> {
        GemKeywordViewModel.provideFactory(viewModelFactory, EpisodeKeywordAndId(args.keyword , args.episodeId))
    }
    override fun initView() {
        initListener()
        initSelectKeyword()
        args.keyword

        repeatOnStarted {
            viewModel.userSelectKeyword.collectLatest {
                checkKeyword(it.keyword)
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

    fun initListener(){
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
    }
    fun checkKeyword(keyword:String){
        binding.fieldCommunication.isSelected = false
        binding.fieldResolve.isSelected = false
        binding.fieldCreative.isSelected = false
        binding.fieldChallenge.isSelected = false
        binding.fieldProfession.isSelected = false
        binding.fieldExcutive.isSelected = false

        when(keyword){
            "커뮤니케이션" -> binding.fieldCommunication.isSelected = true
            "문제 해결" -> binding.fieldResolve.isSelected = true
            "창의성" -> binding.fieldCreative.isSelected = true
            "도전 정신" -> binding.fieldChallenge.isSelected = true
            "전문성" -> binding.fieldProfession.isSelected = true
            "실행력" -> binding.fieldExcutive.isSelected = true
        }
    }

    fun initSelectKeyword(){
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