package com.makeus.daycarat.presentation.fragment.episode

import android.util.Log
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.datatransport.runtime.retries.Retries.retry
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.databinding.FragmentUserInfoBinding
import com.makeus.daycarat.presentation.recyclerview.paging.EpisodeDetailAdatper
import com.makeus.daycarat.presentation.recyclerview.paging.PagingLoadingAdapter
import com.makeus.daycarat.presentation.viewmodel.EpisodeDetailTypeViewModel
import com.makeus.daycarat.presentation.viewmodel.EpisodeViewmodel
import com.makeus.daycarat.util.Extensions.onThrottleClick
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeDetailTypeFragment() : BaseFragment<FragmentEpisodeDetailTypeBinding>(
    FragmentEpisodeDetailTypeBinding::inflate) {

    private val pagingAdapter by lazy {
        EpisodeDetailAdatper()
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailTypeViewModel::class.java)
    }

    override fun initView() {

        val arg: EpisodeDetailTypeFragmentArgs by navArgs()
        val typeItem = arg.typeItem


        if (typeItem.activityTagName == null  ){
            binding.textTitle.text = "${arg.year}년도 ${typeItem.month}월"
            Log.d("GLHESSD" , " ${arg.year} ${typeItem.activityTagName}")
            viewModel.getPagingEpisodeContentOrderByDate(arg.year , typeItem.month)
        }else{
            binding.textTitle.text = typeItem.activityTagName
            viewModel.getPagingEpisodeContentOrderByCount(typeItem.activityTagName!!)
        }
        collectEpisodeContent()


        binding.textCount.text = typeItem.quantity.toString()
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.recyclerContent.adapter = pagingAdapter.withLoadStateFooter(PagingLoadingAdapter{pagingAdapter.retry()})
        pagingAdapter.onclick = { id ->
            findNavController().navigate(R.id.action_episodeDetailTypeFragment_to_episodeSeeContentFragment , bundleOf("episodeId" to id )  )
        }


    }

    fun collectEpisodeContent(){
        repeatOnStarted {
            viewModel.episodeContentList.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)
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

}