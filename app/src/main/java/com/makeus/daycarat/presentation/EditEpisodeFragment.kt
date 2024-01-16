package com.makeus.daycarat.presentation

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.core.view.doOnNextLayout
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEditEpisodeBinding
import com.makeus.daycarat.databinding.FragmentHomeBinding
import com.makeus.daycarat.presentation.spinner.EpisodeSpinner
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisode

class EditEpisodeFragment() : BaseFragment<FragmentEditEpisodeBinding>(
    FragmentEditEpisodeBinding::inflate) {

    override fun initView() {

        binding.textDay.text = parseTimeToEpisode()

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }


        var mAdapter = EpisodeSpinner(requireContext() ,  resources.getStringArray(R.array.episode_header_datas).toList() , 1000)
        binding.layoutEditEpisode.spinnerCategory.adapter = mAdapter
        binding.layoutEditEpisode.spinnerCategory.setSelection(1000)
        //onItemSelected Init 때 호출 안되게 하기 위해 옮김

        binding.layoutEditEpisode.spinnerCategory.doOnNextLayout {
            binding.layoutEditEpisode.spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sortData: Int, p3: Long) {
                    mAdapter.changeSelection(sortData)
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }

    }

}