package com.makeus.daycarat.presentation.fragment.episode

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.recyclerview.paging.EpisodeDetailAdatper
import com.makeus.daycarat.presentation.viewmodel.EpisodeDetailTypeViewModel
import com.makeus.daycarat.presentation.viewmodel.EpisodeViewmodel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailTypeFragment() : BaseFragment<FragmentEpisodeDetailTypeBinding>(
    FragmentEpisodeDetailTypeBinding::inflate) {

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailTypeViewModel::class.java)
    }

    override fun initView() {

        val arg: EpisodeDetailTypeFragmentArgs by navArgs()
        val typeItem = arg.typeItem

        if (typeItem.activityTagName.isNotEmpty()){
            binding.textTitle.text = typeItem.activityTagName
        }else{
            binding.textTitle.text = "${typeItem.month}월"
        }
        binding.textCount.text = typeItem.quantity.toString()
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }


        val pagingAdapter = EpisodeDetailAdatper()
        binding.recyclerContent.adapter = pagingAdapter

        repeatOnStarted {
            viewModel.episodeContents.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)

            }
        }


//        Toast.makeText(context , "${typeItem.activityTagName} month ${typeItem.month} quantity ${typeItem.quantity}", Toast.LENGTH_SHORT).show()

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