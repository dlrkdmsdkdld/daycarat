package com.makeus.daycarat.presentation.fragment.episode

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.data.SoaraContent
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

        repeatOnStarted {
            viewModel.episodeSoara.collectLatest {
                checkAlreadyRegisterSoara(it)
                binding.apply {
                    initNavigateSoara(fieldContent1 , 1 ,it.content1?:"" )
                    initNavigateSoara(fieldContent2 , 2 ,it.content2?:"")
                    initNavigateSoara(fieldContent3 , 3 ,it.content3?:"" )
                    initNavigateSoara(fieldContent4 , 4 ,it.content4?:"" )
                    initNavigateSoara(fieldContent5 , 5 ,it.content5?:"")
                }
            }
        }


        binding.fieldAddPoint.isVisible = SharedPreferenceManager.getInstance().getBoolean(IS_USER_LAST_FOLD_EPISODE , true)
        binding.btnFoleEpiosde.onThrottleClick {
            var now = SharedPreferenceManager.getInstance().getBoolean(IS_USER_LAST_FOLD_EPISODE , true)
            SharedPreferenceManager.getInstance().setBoolean(IS_USER_LAST_FOLD_EPISODE , !now)
            binding.fieldAddPoint.isVisible = !now
        }
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.nextBtn.onThrottleClick {
            findNavController().navigate(R.id.action_soaraFragment_to_completeSoaraFragment , bundleOf("episode_id" to viewModel.episodeConetent.value.episodeId))
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
    fun checkAlreadyRegisterSoara(soara: SoaraContent){
        soara.gemId?.let {
            changeCheckImage(binding.imgArrow1 ,!soara.content1.isNullOrEmpty() , binding.fieldContent1 )
            changeCheckImage(binding.imgArrow2 ,!soara.content2.isNullOrEmpty() , binding.fieldContent2 )
            changeCheckImage(binding.imgArrow3 ,!soara.content3.isNullOrEmpty() , binding.fieldContent3 )
            changeCheckImage(binding.imgArrow4 ,!soara.content4.isNullOrEmpty() , binding.fieldContent4 )
            changeCheckImage(binding.imgArrow5 ,!soara.content5.isNullOrEmpty() , binding.fieldContent5 )
            cheackCompleteSoara()
        }

    }
    fun changeCheckImage(view:ImageView , alreadyDone:Boolean , fieldView:View){
        if (alreadyDone) view.setColorFilter(Color.parseColor("#7241FF"))
        else view.clearColorFilter()
        fieldView.isSelected = alreadyDone
    }
    fun initNavigateSoara(view:View , num : Int , content:String){
        view.onThrottleClick {
            findNavController().navigate(R.id.action_soaraFragment_to_editSoaraFragment ,
                bundleOf("content_num" to num , "soara_content" to content , "episode_id" to viewModel.episodeConetent.value.episodeId )  )
        }
    }
    fun cheackCompleteSoara(){
        binding.nextBtn.isEnabled =  binding.fieldContent1.isSelected && binding.fieldContent2.isSelected && binding.fieldContent3.isSelected && binding.fieldContent4.isSelected && binding.fieldContent5.isSelected
    }

}