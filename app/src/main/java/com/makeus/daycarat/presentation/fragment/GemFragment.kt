package com.makeus.daycarat.presentation.fragment

import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEpisodeBinding
import com.makeus.daycarat.databinding.FragmentGemBinding
import com.makeus.daycarat.presentation.viewmodel.GemViewModel
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.UiManager.setGemImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GemFragment() : BaseFragment<FragmentGemBinding>(
    FragmentGemBinding::inflate) {
    private val mainViewModel: MainViewmodel by activityViewModels()
    private val gemviewmodel by lazy {
        ViewModelProvider(this).get(GemViewModel::class.java)
    }
    override fun initView() {
        gemviewmodel.initData()
        initCollector()
        initClickListener()


    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }
    fun initClickListener(){
        binding.fieldCommunication.setOnClickListener {
            goDetailFragment("커뮤니케이션" , gemviewmodel.gemTypeCount.value.communication)
        }
        binding.fieldResolve.setOnClickListener {
            goDetailFragment("문제 해결", gemviewmodel.gemTypeCount.value.problemSolving)
        }
        binding.fieldCreative.setOnClickListener {
            goDetailFragment("창의성", gemviewmodel.gemTypeCount.value.creativity)
        }
        binding.fieldChallenge.setOnClickListener {
            goDetailFragment("도전 정신", gemviewmodel.gemTypeCount.value.challengeSpirit)
        }
        binding.fieldProfession.setOnClickListener {
            goDetailFragment("전문성", gemviewmodel.gemTypeCount.value.proficiency)
        }
        binding.fieldExcutive.setOnClickListener {
            goDetailFragment("실행력", gemviewmodel.gemTypeCount.value.execution)
        }
        binding.fieldNone.setOnClickListener {
            goDetailFragment("미선택", gemviewmodel.gemTypeCount.value.unset)
        }
    }
    fun goDetailFragment(keyword:String , itemCount : Int){
        findNavController().navigate(R.id.action_gemFragment_to_gemDetailFragment , bundleOf("keyword" to keyword , "item_count" to itemCount ))
    }

    fun initCollector(){
        repeatOnStarted {
            mainViewModel.userData.collect {
                binding.textNickname.text = it.nickname
                binding.textAdvantage.text = it.strength
                Glide.with(this@GemFragment)
                    .load(it.profileImage)
                    .error(R.drawable.bg_home)
                    .into(binding.imgProfile)
            }
        }

        repeatOnStarted {
            gemviewmodel.gemTotalCount.collectLatest {
                binding.textGemCount.text = it.gemCount.toString()
            }
        }

        repeatOnStarted {
            gemviewmodel.gemTypeCount.collectLatest {
                binding.textCommunication.text = it.communication.toString()
                binding.textResolve.text = it.problemSolving.toString()
                binding.textCreative.text = it.creativity.toString()
                binding.textChallenge.text = it.challengeSpirit.toString()
                binding.textProfession.text = it.proficiency.toString()
                binding.textExcutive.text = it.execution.toString()
                binding.textNone.text = it.unset.toString()
            }
        }

        repeatOnStarted {
            gemviewmodel.gemMonthCount.collectLatest {
                binding.textMonthCount.text = it.gemCount.toString()
            }
        }
        repeatOnStarted {
            gemviewmodel.mostKeyword.collectLatest {
                setGemImage(it.episodeKeyword , binding.imgMostGem)
                binding.textKeyword.text = it.episodeKeyword
            }
        }
        repeatOnStarted {
            gemviewmodel.mostActivityTag.collectLatest {
                binding.textActivity.text = it.activityTag
            }
        }
    }

}