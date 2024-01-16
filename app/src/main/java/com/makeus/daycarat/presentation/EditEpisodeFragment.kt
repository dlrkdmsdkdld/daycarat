package com.makeus.daycarat.presentation

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.doOnNextLayout
import androidx.lifecycle.ViewModelProvider
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEditEpisodeBinding
import com.makeus.daycarat.databinding.FragmentHomeBinding
import com.makeus.daycarat.presentation.spinner.EpisodeSpinner
import com.makeus.daycarat.presentation.viewmodel.EditEpisodeViewmodel
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisode
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEpisodeFragment() : BaseFragment<FragmentEditEpisodeBinding>(
    FragmentEditEpisodeBinding::inflate), TextWatcher {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EditEpisodeViewmodel::class.java)
    }
    var spinnerArray = ArrayList<Spinner>()
    var editArray = ArrayList<EditText>()

    override fun initView() {

        binding.textDay.text = parseTimeToEpisode()

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }
        var arrayData = resources.getStringArray(R.array.episode_header_datas)

        var mAdapter = EpisodeSpinner(requireContext() ,  resources.getStringArray(R.array.episode_header_datas).toList() , 1000)
        binding.layoutEditEpisode.spinnerCategory.adapter = mAdapter
        binding.layoutEditEpisode.spinnerCategory.setSelection(1000)
        //onItemSelected Init 때 호출 안되게 하기 위해 옮김

        binding.layoutEditEpisode.spinnerCategory.doOnNextLayout {
            binding.layoutEditEpisode.spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sortData: Int, p3: Long) {
                    mAdapter.changeSelection(sortData)
                    viewModel.changeEpidoseContentType(0 ,arrayData.getOrNull(sortData))
                    chcekSaveBtn()
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
        spinnerArray.add(binding.layoutEditEpisode.spinnerCategory)
        editArray.add(binding.layoutEditEpisode.editEpisode)


        binding.editTitle.addTextChangedListener(this)
        binding.editTag.addTextChangedListener(this)
        binding.layoutEditEpisode.editEpisode.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.changeEpidoseContentText(0 , p0.toString())
                chcekSaveBtn()
            }

        })

        binding.btnSave.setOnClickListener{
            Toast.makeText(requireContext(),"sdf",Toast.LENGTH_SHORT).show()
        }



    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        chcekSaveBtn()

    }
    fun chcekSaveBtn(){
        var firstContent = viewModel.episodeContent.value.get(0)
        binding.btnSave.isEnabled = binding.editTitle.text.isNotEmpty() &&   binding.editTag.text.isNotEmpty() && firstContent.content.isNotEmpty() && firstContent.episodeContentType.isNotEmpty()

    }

}