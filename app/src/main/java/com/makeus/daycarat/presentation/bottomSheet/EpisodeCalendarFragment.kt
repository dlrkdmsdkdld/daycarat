package com.makeus.daycarat.presentation.bottomSheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.makeus.daycarat.databinding.BottomEditCalenderBinding

class EpisodeCalendarFragment : BottomSheetDialogFragment() {
    var onclick:((Calendar) -> Unit)? = null
    val binding by lazy { BottomEditCalenderBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.calendarMonth.setOnCalendarSelectListener(object :
            CalendarView.OnCalendarSelectListener {
            override fun onCalendarOutOfRange(calendar: Calendar?) {
            }

            override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
                calendar?.let {
                    onclick?.invoke(calendar)
                }
            }

        })



        binding.btnNext.setOnClickListener {
            binding.calendarMonth.scrollToNext()

        }
        binding.btnPrevious.setOnClickListener {
            binding.calendarMonth.scrollToPre()

        }
        binding.textTitle.text = "${binding.calendarMonth.curYear}년 ${binding.calendarMonth.curMonth}월"
        binding.calendarMonth.setOnMonthChangeListener { year, month ->
            binding.textTitle.text ="${year}년 ${month}월"
        }
    }
}