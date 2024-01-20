package com.makeus.daycarat.presentation

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.doOnNextLayout
import androidx.lifecycle.ViewModelProvider
import com.makeus.daycarat.R
import com.makeus.daycarat.base.BaseFragment
import com.makeus.daycarat.databinding.FragmentEditEpisodeBinding
import com.makeus.daycarat.databinding.LayoutEditEdpisodeBinding
import com.makeus.daycarat.presentation.bottomSheet.EpisodeCalendarFragment
import com.makeus.daycarat.presentation.recyclerview.SearchTagAdapter
import com.makeus.daycarat.presentation.spinner.EpisodeSpinner
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.presentation.viewmodel.EditEpisodeViewmodel
import com.makeus.daycarat.util.Extensions.repeatOnStarted
import com.makeus.daycarat.util.Extensions.statusBarHeight
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisode
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisodeWithWeekDay
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditEpisodeFragment() : BaseFragment<FragmentEditEpisodeBinding>(
    FragmentEditEpisodeBinding::inflate
) {
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
        binding.fieldNewEdit.addView(inflateEditField())

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


        binding.btnCalendar.setOnClickListener {
            var bottomDialog = EpisodeCalendarFragment()
            bottomDialog.onclick = {
                viewModel.updateDay(it.year , it.month , it.day)
            }
            activity?.supportFragmentManager?.let { it1 -> bottomDialog.show(it1, "BottomCalendarDialog") }
        }

        binding.fieldAll.setOnClickListener {
            hideKeyboard()
        }
        initEditTag()
    }

    override fun initStatusBar() {
        binding.fieldMain.setPadding(
            0,
            requireActivity().statusBarHeight(),
            0,
            0
        )
    }
    private fun hideKeyboard() {
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

    }
    fun chcekSaveBtn() {
        var isEnable = true
        viewModel.episodeContent.value.forEachIndexed { index, episodeContent ->
            if (episodeContent.content.isEmpty() || episodeContent.episodeContentType.isEmpty()) {
                isEnable = false
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

    fun initEditTag(){
        searchAdapter.onclick = { selectText ->
            hideKeyboard()
            binding.editTag.setText(selectText)
            binding.editTag.clearFocus()
        }
        binding.recyclerSearch.adapter = searchAdapter
        binding.editTag.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchAdapter.filter.filter(p0)
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
        binding.editTag.setOnFocusChangeListener { view, b ->
            if (b) {
                binding.fieldSearch.visibility = View.VISIBLE
            }
            else binding.fieldSearch.visibility = View.GONE

        }
    }

}

