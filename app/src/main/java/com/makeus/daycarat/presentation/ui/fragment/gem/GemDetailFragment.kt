package com.makeus.daycarat.presentation.fragment.gem

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.presentation.recyclerview.paging.GemDetailAdatper
import com.makeus.daycarat.presentation.recyclerview.paging.PagingLoadingAdapter
import com.makeus.daycarat.presentation.viewmodel.gem.GemDetailViewModel
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class GemDetailFragment() : BaseFragment<FragmentEpisodeDetailTypeBinding>(
    FragmentEpisodeDetailTypeBinding::inflate) {
    val args: GemDetailFragmentArgs by navArgs()

    private lateinit var pagingAdapter: GemDetailAdatper


    private val viewModel by lazy {
        ViewModelProvider(this).get(GemDetailViewModel::class.java)
    }


    override fun initView() {
        viewModel.startPaging(args.keyword , args.itemCount)
        pagingAdapter = GemDetailAdatper(viewModel.keyword)
        binding.textTitle.text = viewModel.keyword
        binding.textCount.text = viewModel.itemCount.toString()

        pagingAdapter.onclick ={ id ->
            findNavController().navigate(R.id.action_gemDetailFragment_to_gemContentFragment , bundleOf( "episode_id" to id , "keyword" to viewModel.keyword ))
        }
        binding.recyclerContent.adapter = pagingAdapter.withLoadStateFooter(PagingLoadingAdapter{pagingAdapter.retry()})
        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading  && loadState.append.endOfPaginationReached && pagingAdapter.itemCount < 1){
                binding.fieldEmpty.visibility = View.VISIBLE
                binding.recyclerContent.visibility = View.GONE
            }else{
                binding.fieldEmpty.visibility = View.GONE
                binding.recyclerContent.visibility = View.VISIBLE
            }
        }

        repeatOnStarted {
            viewModel.gemList.collect{
                pagingAdapter.submitData(it)
            }
        }

        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }

        repeatOnStarted {
            viewModel.gemTypeCount.collectLatest {
                when(viewModel.keyword){
                    "커뮤니케이션" -> binding.textCount.text = it.communication.toString()
                    "문제 해결" ->binding.textCount.text = it.problemSolving.toString()
                    "창의성" ->binding.textCount.text = it.creativity.toString()
                    "도전 정신" ->binding.textCount.text = it.challengeSpirit.toString()
                    "전문성" ->binding.textCount.text = it.proficiency.toString()
                    "실행력" ->binding.textCount.text = it.execution.toString()
                    "미선택" ->binding.textCount.text = it.unset.toString()
                }
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