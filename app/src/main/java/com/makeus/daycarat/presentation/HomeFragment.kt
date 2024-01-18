package com.makeus.daycarat.presentation

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentHomeBinding
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.presentation.viewmodel.MainViewmodel
import com.makeus.daycarat.presentation.viewmodel.UserDataViewmodel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.repeatOnStarted
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

        binding.fieldNews1.setOnClickListener{}


    }

}