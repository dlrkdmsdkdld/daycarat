package com.makeus.daycarat.presentation.fragment.episode

import android.os.Build
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
import androidx.navigation.fragment.findNavController
import com.makeus.daycarat.R
import com.makeus.daycarat.presentation.util.base.BaseFragment
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.databinding.FragmentEditEpisodeBinding
import com.makeus.daycarat.databinding.LayoutEditEdpisodeBinding
import com.makeus.daycarat.presentation.MainActivity
import com.makeus.daycarat.presentation.bottomSheet.EpisodeCalendarFragment
import com.makeus.daycarat.presentation.recyclerview.SearchTagAdapter
import com.makeus.daycarat.presentation.spinner.EpisodeSpinner
import com.makeus.daycarat.presentation.viewmodel.episode.EditEpisodeViewmodel
import com.makeus.daycarat.presentation.util.Extensions.HideKeyBoard
import com.makeus.daycarat.presentation.util.Extensions.onThrottleClick
import com.makeus.daycarat.presentation.util.Extensions.repeatOnStarted
import com.makeus.daycarat.presentation.util.Extensions.statusBarHeight
import com.makeus.daycarat.presentation.util.SharedPreferenceManager
import com.makeus.daycarat.presentation.util.TimeUtil.parseTimeToEpisode
import com.makeus.daycarat.presentation.util.TimeUtil.parseTimeToEpisodeForEdit
import com.makeus.daycarat.presentation.util.TimeUtil.parseTimeToEpisodeWithWeekDay
import com.makeus.daycarat.presentation.util.UiEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EditEpisodeFragment() : BaseFragment<FragmentEditEpisodeBinding>(
    FragmentEditEpisodeBinding::inflate
) {
    private val viewModel by lazy {
        ViewModelProvider(this).get(EditEpisodeViewmodel::class.java)
    }
    private val searchAdapter by lazy {
        SearchTagAdapter(SharedPreferenceManager.getInstance().getEpisodeActivityTags() ,::clickSearchRecyclerView)
    }

    var spinnerArray = ArrayList<Spinner>()
    var editArray = ArrayList<EditText>()
    var arrayData = arrayOf<String>()

    override fun initView() {


        arrayData = resources.getStringArray(R.array.episode_header_datas)
        binding.textDay.text = parseTimeToEpisode()
        initEditTag()

        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnSave.onThrottleClick { // 중복클릭방지
            viewModel.clickSaveBtn(
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
                binding.textDay.text = "$day (${parseTimeToEpisodeWithWeekDay(day)})"
            }
        }
        repeatOnStarted {
            viewModel.flowEvent.collect { event ->
                when (event) {
                    is UiEvent.LoadingEvent -> {
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
                viewModel.updateDay(it.year, it.month, it.day)
            }
            activity?.supportFragmentManager?.let { it1 ->
                bottomDialog.show(
                    it1,
                    "BottomCalendarDialog"
                )
            }
        }

        binding.fieldAll.setOnClickListener {
            hideKeyboard()
        }


        var data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("episodeContent", EpisodeFullContent::class.java)
        } else {
            arguments?.getParcelable<EpisodeFullContent>("episodeContent")
        }
        data?.let {
            editSetting(it)
        } ?: kotlin.run {
            binding.fieldNewEdit.addView(inflateEditField())
        } // 신규등록인경우에만 디폴트로있음

    }

    private fun editSetting(editData: EpisodeFullContent) {
//        viewModel.
        binding.editTitle.setText(editData.title)
        binding.editTag.setText(editData.activityTagName)
        viewModel.updateDay(parseTimeToEpisodeForEdit(editData.selectedDate))
        viewModel.setEditMode(editData.episodeId)
        editData.episodeContents.forEachIndexed { index, episodeContent ->
            var arrayIndex = arrayData.indexOf(episodeContent.episodeContentType)
            binding.fieldNewEdit.addView(inflateEditField(arrayIndex))
            viewModel.userSelectSpinner(arrayIndex)
            viewModel.userSelectSaveLastSpinner(index, arrayIndex)
            viewModel.changeEpidoseContentType(arrayIndex, arrayData.getOrNull(arrayIndex))
            viewModel.changeEpidoseContentText(index, episodeContent.content)
            editArray.getOrNull(index)?.setText(episodeContent.content)
        }
        chcekSaveBtn()

    }


    override fun onDestroyView() {
//        isDestroyBinding = false
        super.onDestroyView()
//        _binding = null
        // 주의해서 사용 :: 안드로이드에서 탭 변경해서 fragment 변경시 기존
        // fragment들은 ondestory를 타지 않고 오직 onDestroyView만 타기때문에 메모리 누수 주위


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
        activity?.HideKeyBoard()
    }

    fun chcekSaveBtn() {
        var isEnable = true
        if (binding.editTag.text.isNullOrEmpty()) isEnable = false
        viewModel.episodeContent.value.forEachIndexed { index, episodeContent ->
            Log.d("GHLEE" , "episodeContent ${episodeContent.content} episodeContentType ${episodeContent.episodeContentType}")
            if (episodeContent.content.isEmpty() || episodeContent.episodeContentType.isEmpty()) {
                isEnable = false
                return@forEachIndexed
            }
        }
        //필수 작성 사항 -날짜  작성항목 + 항목내용 추가된 갯수만큼 써야함  - 어짜피 날짜는 디폴트로 오늘날짜임
        binding.btnSave.isEnabled = isEnable
    }

    fun inflateEditField(selection: Int = 1000): View {
        var editBining = LayoutEditEdpisodeBinding.inflate(layoutInflater)
        var pos = viewModel.plusEditCount()

        var mAdapter = EpisodeSpinner(requireContext(), arrayData.toList(), 1000, viewModel)
        editBining.spinnerCategory.adapter = mAdapter
        editBining.spinnerCategory.setSelection(selection)

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

    fun clickSearchRecyclerView(data:String){
        hideKeyboard()
        binding.editTag.setText(data)
        binding.editTag.clearFocus()
    }

    fun initEditTag() {
        binding.recyclerSearch.adapter = searchAdapter
        binding.editTag.addTextChangedListener(object : TextWatcher {
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
            } else binding.fieldSearch.visibility = View.GONE

        }
    }

}

