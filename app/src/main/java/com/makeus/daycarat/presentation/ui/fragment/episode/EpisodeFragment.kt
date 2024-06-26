package com.makeus.daycarat.presentation.fragment.episode

import android.view.View
import android.widget.AdapterView
import androidx.core.os.bundleOf
import androidx.core.view.doOnNextLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.databinding.FragmentEpisodeBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.recyclerview.EpisodeTagAdapter
import com.makeus.daycarat.presentation.spinner.EpisodeCardSpinner
import com.makeus.daycarat.presentation.util.EpisodeTagViewType
import com.makeus.daycarat.presentation.viewmodel.episode.EpisodeViewmodel
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import com.makeus.daycarat.presentation.util.UiManager.setSpan
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class EpisodeFragment() : BaseFragment<FragmentEpisodeBinding>(
    FragmentEpisodeBinding::inflate) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EpisodeViewmodel::class.java)
    }
    private val episodeAdapter by lazy {
        EpisodeTagAdapter( EpisodeTagViewType.Activity ,::clickEpisodeItem)
    }

    fun clickEpisodeItem(data: EpisodeActivityCounter){
        var tmpBundle = if(data.activityTagName == null ) bundleOf("typeItem" to data
            , "year" to viewModel.selectYear) else bundleOf("typeItem" to data , "year" to 0)
        findNavController().navigate(R.id.action_episodeFragment_to_episodeDetailTypeFragment , tmpBundle )

    }

    override fun initView() {
        binding.recyclerEpisode.adapter = episodeAdapter
        viewModel.getTotalGemCount()
        if (binding.chipActivity.isChecked){
            viewModel.getActivityTagOderByCount()
            binding.spinnerYear.visibility = View.GONE
        }else{
            binding.spinnerYear.visibility = View.VISIBLE
            viewModel.getActivityTagOderByDate()
        }

        repeatOnStarted {
            viewModel.episodeCountList.collectLatest {
                if (it.isEmpty()){
                    binding.fieldNoEpi.visibility = View.VISIBLE
                    binding.recyclerEpisode.visibility = View.GONE
                    binding.textEmptyEpisode.setOnClickListener {
                        (activity as MainActivity).binding.btnCenter.callOnClick()
                    }
                }else{
                    binding.fieldNoEpi.visibility = View.GONE
                    binding.recyclerEpisode.visibility = View.VISIBLE
                    if (binding.chipActivity.isChecked) episodeAdapter.changeType(it , EpisodeTagViewType.Activity)
                    else episodeAdapter.changeType(it , EpisodeTagViewType.Date)
                }


            }
        }

        repeatOnStarted {
            viewModel.episodeTotalCount.collectLatest {
                binding.textTotalCount.text = setSpan(resources.getColor(R.color.main , null ) , 0 , it.episodeCount.toString().length , resources.getString(R.string.episode_top_title , it.episodeCount) )
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



    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }

}