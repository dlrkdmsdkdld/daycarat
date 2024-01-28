package com.makeus.daycarat.presentation.fragment.gem

import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.presentation.recyclerview.paging.GemDetailAdatper
import com.makeus.daycarat.presentation.recyclerview.paging.PagingLoadingAdapter
import com.makeus.daycarat.presentation.viewmodel.gem.GemDetailViewModel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint


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
            findNavController().navigate(R.id.action_gemDetailFragment_to_gemContentFragment , bundleOf( "episode_id" to id ))
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