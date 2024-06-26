package com.makeus.daycarat.presentation.fragment.episode

import android.util.Log
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEpisodeDetailTypeBinding
import com.makeus.daycarat.presentation.recyclerview.paging.EpisodeDetailAdatper
import com.makeus.daycarat.presentation.recyclerview.paging.PagingLoadingAdapter
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeDetailTypeViewModel
import com.makeus.daycarat.presentation.util.Constant
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailTypeFragment() : BaseFragment<FragmentEpisodeDetailTypeBinding>(
    FragmentEpisodeDetailTypeBinding::inflate) {

    private val pagingAdapter by lazy {
        EpisodeDetailAdatper(::clickDeatailItem)
    }

    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailTypeViewModel::class.java)
    }

    override fun initView() {
        val arg: EpisodeDetailTypeFragmentArgs by navArgs()
        viewModel.startPaging(arg.year , arg.typeItem)


        if (viewModel.typeItem?.activityTagName == null  ){
            binding.textTitle.text = "${viewModel.year}년도 ${viewModel.typeItem?.month}월"
        }else{
            binding.textTitle.text = viewModel.typeItem?.activityTagName
//            viewModel.getPagingEpisodeContentOrderByCount(typeItem.activityTagName!!)
        }
        collectEpisodeContent()


        binding.textCount.text = viewModel.typeItem?.quantity.toString() //TODO 서버에서 개수 가져와야함
        binding.btnBack.onThrottleClick {
            findNavController().popBackStack()
        }
        binding.recyclerContent.adapter = pagingAdapter.withLoadStateFooter(PagingLoadingAdapter{pagingAdapter.retry()})


        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("key")?.observe(viewLifecycleOwner) {result ->
            if (result.equals(Constant.EPISODE_REMOVE_SECCESS)) {
                binding.textCount.text = viewModel.removeEpisodeEvent().toString()
            }else{

            }
        }


    }

    fun clickDeatailItem(id:Int){
        findNavController().navigate(R.id.action_episodeDetailTypeFragment_to_episodeSeeContentFragment
            , bundleOf("episodeId" to id )  )

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