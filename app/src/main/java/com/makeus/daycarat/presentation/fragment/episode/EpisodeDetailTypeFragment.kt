package com.makeus.daycarat.presentation.fragment.episode

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.datatransport.runtime.retries.Retries.retry
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

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailTypeViewModel::class.java)
    }

    override fun initView() {

        val arg: EpisodeDetailTypeFragmentArgs by navArgs()
        val typeItem = arg.typeItem

        if (typeItem.activityTagName.isNullOrEmpty()){
            binding.textTitle.text = "${typeItem.month}월"
        }else{
            binding.textTitle.text = typeItem.activityTagName
        }
        binding.textCount.text = typeItem.quantity.toString()
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }


        val pagingAdapter = EpisodeDetailAdatper()
//        pagingAdapter.withLoadStateHeaderAndFooter(
//                header = PagingLoadingAdapter(pagingAdapter::retry),
//                footer = PagingLoadingAdapter(pagingAdapter::retry) //TODO 페이징 로딩 어뎁터인데 정상 작동하는지는 체크필요
//            )

        binding.recyclerContent.adapter = pagingAdapter.withLoadStateFooter(PagingLoadingAdapter{pagingAdapter.retry()})
        binding.recyclerContent.apply {
            layoutManager = LinearLayoutManager(context)
        }


        repeatOnStarted {
            viewModel.episodeContents.collect{ pagingData ->
                pagingAdapter.submitData(pagingData)

            }
        }

        repeatOnStarted {
            pagingAdapter.loadStateFlow.collectLatest {  value: CombinedLoadStates ->

                Log.d("GHLEESIBAL","Loading ${value.refresh is LoadState.Loading} retry ${value.refresh !is LoadState.Loading} Error ${value.refresh is LoadState.Error}")
                value.refresh

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