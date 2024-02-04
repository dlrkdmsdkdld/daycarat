package com.makeus.daycarat.presentation.fragment

import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeRecent
import com.makeus.daycarat.databinding.FragmentHomeBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment() : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }
    private val mainViewModel: MainViewmodel by activityViewModels()

    override fun initView() {
        Log.d(Constant.TAG ,"HomeFragment")
        viewModel.apply {
            updateUserInfo()
            getRecentEpisode()
        }
        repeatOnStarted {
            viewModel.episodeCount.collectLatest {
                binding.textCountEpisode.text = it.toString()

            }
        }
        repeatOnStarted {
            mainViewModel.userData.collectLatest {
                binding.textTopTitle.text = resources.getString(R.string.home_user_title , it.nickname)
            }
        }

        observeRecentEpisode()

        binding.fieldNews1.setOnClickListener{}
    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }



    fun observeRecentEpisode(){
        repeatOnStarted {
            viewModel.recentEpisode.collectLatest{ value: List<EpisodeRecent> ->
                if (value.isEmpty()){
                    binding.fieldNoEpi.visibility = View.VISIBLE
                    binding.fieldYesEpi.visibility = View.GONE
                    binding.textEmptyEpisode.setOnClickListener {
                        (activity as MainActivity).binding.btnCenter.callOnClick()
                    }
                }else{
                    binding.fieldNoEpi.visibility = View.GONE
                    binding.fieldYesEpi.visibility = View.VISIBLE
                    binding.fieldRecent1.visibility = View.VISIBLE
                    binding.fieldRecent2.visibility = View.GONE
                    binding.fieldRecent3.visibility = View.GONE
                    value.forEachIndexed { index, episodeRecent ->
                        when(index){
                            0 -> {
                                binding.textRecentTitle1.text = episodeRecent.title
                                binding.textRecentDes1.text = episodeRecent.time
                                binding.fieldRecent1.setOnClickListener {
                                    findNavController().navigate(R.id.action_homeFragment_to_episodeSeeContentFragment ,
                                        bundleOf( "episodeId" to episodeRecent.id ) )

                                }
                            }
                            1 -> {
                                binding.fieldRecent2.visibility = View.VISIBLE
                                binding.textRecentTitle2.text = episodeRecent.title
                                binding.textRecentDes2.text = episodeRecent.time
                                binding.fieldRecent2.setOnClickListener {
                                    findNavController().navigate(R.id.action_homeFragment_to_episodeSeeContentFragment ,
                                        bundleOf( "episodeId" to episodeRecent.id ) )
                                }
                            }
                            2 -> {
                                binding.fieldRecent3.visibility = View.VISIBLE
                                binding.textRecentTitle3.text = episodeRecent.title
                                binding.textRecentDes3.text = episodeRecent.time
                                binding.fieldRecent3.setOnClickListener {
                                    findNavController().navigate(R.id.action_homeFragment_to_episodeSeeContentFragment ,
                                        bundleOf( "episodeId" to episodeRecent.id ) )
                                }
                            }

                        }
                    }
                }
            }
        }
    }

}