package com.makeus.daycarat.presentation.fragment.episode

import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.view.doOnNextLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.FragmentEpisodeBinding
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagAdapter
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagViewType
import com.makeus.daycarat.presentation.spinner.EpisodeCardSpinner
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EpisodeFragment() : BaseFragment<FragmentEpisodeBinding>(
    FragmentEpisodeBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeViewmodel::class.java)
    }
    private val episodeAdapter by lazy {
        EpisodeTagAdapter(listOf<EpisodeActivityCounter>() , EpisodeTagViewType.Activity)
    }

    override fun initView() {
        binding.recyclerEpisode.adapter = episodeAdapter
        episodeAdapter.onClick = {
            var tmpBundle = if(it.activityTagName == null ) bundleOf("typeItem" to it , "year" to viewModel.selectYear) else bundleOf("typeItem" to it , "year" to 0)
            findNavController().navigate(R.id.action_episodeFragment_to_episodeDetailTypeFragment , tmpBundle )
        }


        repeatOnStarted {
            viewModel.episodeCountList.collectLatest {
                if (binding.chipActivity.isChecked) episodeAdapter.changeType(it , EpisodeTagViewType.Activity)
                else episodeAdapter.changeType(it , EpisodeTagViewType.Date)
            }
        }
        initChipGroup()
        initSpinner()


    }
    fun initChipGroup(){
        binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (binding.chipActivity.isChecked){
                viewModel.getActivityTagOderByCount()
                binding.spinnerYear.visibility = View.GONE
            }else{
                binding.spinnerYear.visibility = View.VISIBLE
                viewModel.getActivityTagOderByDate()
            }
        }
    }

    fun initSpinner(){
        binding.spinnerYear.adapter = EpisodeCardSpinner(requireContext() , resources.getStringArray(R.array.des_years).toList())
        binding.spinnerYear.doOnNextLayout {
            binding.spinnerYear.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sortData: Int, p3: Long) {
                    resources.getStringArray(R.array.des_years).getOrNull(sortData)?.let {
                        viewModel.getActivityTagOderByDate(it.toInt())
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.chipActivity.isChecked){
            viewModel.getActivityTagOderByCount()
            binding.spinnerYear.visibility = View.GONE
        }else{
            binding.spinnerYear.visibility = View.VISIBLE
            viewModel.getActivityTagOderByDate()
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