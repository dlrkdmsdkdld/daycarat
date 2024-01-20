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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView.OnCalendarSelectListener
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.BottomEditCalenderBinding
import com.makeus.daycarat.databinding.FragmentEditEpisodeBinding
import com.makeus.daycarat.databinding.FragmentHomeBinding
import com.makeus.daycarat.databinding.LayoutEditEdpisodeBinding
import com.makeus.daycarat.presentation.calendar.CustomMonthCalendar
import com.makeus.daycarat.presentation.dialog.BottomSheetCalendar
import com.makeus.daycarat.presentation.recyclerview.SearchTagAdapter
import com.makeus.daycarat.presentation.spinner.EpisodeSpinner
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.EditEpisodeViewmodel
import com.makeus.daycarat.presentation.viewmodel.HomeViewModel
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisode
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisodeWithWeekDay
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditEpisodeFragment() : BaseFragment<FragmentEditEpisodeBinding>(
    FragmentEditEpisodeBinding::inflate
), TextWatcher {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EditEpisodeViewmodel::class.java)
    }
    private val searchAdapter by lazy {
        SearchTagAdapter(SharedPreferenceManager.getInstance().getEpisodeActivityTags())
    }

    var spinnerArray = ArrayList<Spinner>()
    var editArray = ArrayList<EditText>()
    var arrayData = arrayOf<String>()

    override fun initView() {
        arrayData = resources.getStringArray(R.array.episode_header_datas)
        binding.textDay.text = parseTimeToEpisode()

        binding.btnBack.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }


        binding.btnSave.setOnClickListener {
            viewModel.registerEpisode(
                binding.editTitle.text.toString(),
                binding.editTag.text.toString()
            )
        }

        binding.btnAddEdit.setOnClickListener {
            if (spinnerArray.size != arrayData.size) {
                binding.fieldNewEdit.addView(inflateEditField())
                binding.btnSave.isEnabled = false
            } else Toast.makeText(requireContext(), "최대 개수를 초과했어요!", Toast.LENGTH_SHORT).show()
        }
//        initSpinner()

//        initEditText()
        binding.fieldNewEdit.addView(inflateEditField())


        repeatOnStarted {
            viewModel.episodeDay.collect { day ->
                Log.d("GHLEE", "day $day")
                binding.textDay.text = "$day (${parseTimeToEpisodeWithWeekDay(day)})"
            }
        }
        repeatOnStarted {
            viewModel.flowEvent.collect { event ->
                when (event) {
                    is AuthViewmodel.UiEvent.LoadingEvent -> {
                        (activity as MainActivity).loadingDialog.show()
                    }

                    else -> {
                        (activity as MainActivity).loadingDialog.dismiss()
                        (activity as MainActivity).navController.popBackStack()
                    }
                }
            }
        }
        searchAdapter.onclick = { selectText ->

        }
        binding.recyclerSearch.adapter = searchAdapter
        binding.editTag.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.recyclerSearch.visibility = View.VISIBLE
            }
            else binding.recyclerSearch.visibility = View.GONE

        }

        binding.btnCalendar.setOnClickListener {

            val bottomSheetView = BottomEditCalenderBinding.inflate(layoutInflater)
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            bottomSheetDialog.setContentView(bottomSheetView.root)
            bottomSheetView.calendarMonth.setOnCalendarSelectListener(object :OnCalendarSelectListener{
                override fun onCalendarOutOfRange(calendar: Calendar?) {
                }

                override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                    Log.d("GHLEECA" ,"calendar ${calendar} ${calendar?.day}")
                }

            })
            bottomSheetDialog.show()

        }

    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun afterTextChanged(p0: Editable?) {
        chcekSaveBtn()

    }

    fun chcekSaveBtn() {
        var isEnable = true
        viewModel.episodeContent.value.forEachIndexed { index, episodeContent ->
            if (episodeContent.content.isEmpty() || episodeContent.episodeContentType.isEmpty()) {
                isEnable = false
                Log.d(
                    "GHALEE",
                    ".content.isEmpty() ${episodeContent.content.isEmpty()}  ${episodeContent.episodeContentType.isEmpty()}"
                )
                return@forEachIndexed
            }
        }
        //필수 작성 사항 -날짜  작성항목 + 항목내용 추가된 갯수만큼 써야함  - 어짜피 날짜는 디폴트로 오늘날짜임
        binding.btnSave.isEnabled = isEnable
    }

    fun inflateEditField(): View {
        var editBining = LayoutEditEdpisodeBinding.inflate(layoutInflater)
        var pos = viewModel.plusEditCount()

        var mAdapter = EpisodeSpinner(requireContext(), arrayData.toList(), 1000, viewModel)
        editBining.spinnerCategory.adapter = mAdapter
        editBining.spinnerCategory.setSelection(1000)

        editBining.spinnerCategory.doOnNextLayout {
            editBining.spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    sortData: Int,
                    p3: Long
                ) {
                    mAdapter.changeSelection(sortData)
                    viewModel.userUnSelectSpinner(pos)
                    viewModel.userSelectSaveLastSpinner(pos, sortData)
                    viewModel.changeEpidoseContentType(pos, arrayData.getOrNull(sortData))
                    viewModel.userSelectSpinner(sortData)
                    chcekSaveBtn()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
        }
        editBining.editEpisode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.changeEpidoseContentText(pos, p0.toString())
                chcekSaveBtn()
            }

        })
        spinnerArray.add(editBining.spinnerCategory)
        editArray.add(editBining.editEpisode)

        return editBining.root

    }

}

//    fun initEditText(){
//        binding.layoutEditEpisode.editEpisode.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                viewModel.changeEpidoseContentText(0 , p0.toString())
//                chcekSaveBtn()
//            }
//
//        })
//        binding.editTitle.addTextChangedListener(this)
//        binding.editTag.addTextChangedListener(object :TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                binding.recyclerSearch.visibility = View.VISIBLE
//                searchAdapter?.filter?.filter(p0)
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//
//        })
//        editArray.add(binding.layoutEditEpisode.editEpisode)
//

//
//    }
//    fun initSpinner(){
//        var mAdapter = EpisodeSpinner(requireContext() ,  arrayData.toList() , 1000 , viewModel)
//        binding.layoutEditEpisode.spinnerCategory.adapter = mAdapter
//        binding.layoutEditEpisode.spinnerCategory.setSelection(1000)
//        //onItemSelected Init 때 호출 안되게 하기 위해 옮김
//
//        binding.layoutEditEpisode.spinnerCategory.doOnNextLayout {
//            binding.layoutEditEpisode.spinnerCategory.onItemSelectedListener = object : OnItemSelectedListener{
//                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, sortData: Int, p3: Long) {
//                    mAdapter.changeSelection(sortData)
//                    viewModel.userUnSelectSpinner(0)
//                    viewModel.changeEpidoseContentType(0 ,arrayData.getOrNull(sortData))
//                    viewModel.userSelectSaveLastSpinner(0 , sortData)
//                    viewModel.userSelectSpinner(sortData)
//                    chcekSaveBtn()
//                }
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//                }
//            }
//        }
//        spinnerArray.add(binding.layoutEditEpisode.spinnerCategory)
//    }