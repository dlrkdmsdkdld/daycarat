package com.makeus.daycarat.util

import android.util.Log
import com.makeus.daycarat.util.Constant.TAG
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeUtil {

    fun parseTimeToYear():Int{
        val tmp = System.currentTimeMillis()
        val dateFormat= SimpleDateFormat("YYYY", Locale("En", "KR"))
        val parseYear=dateFormat.format(tmp)
        Log.d(TAG,"현재 날짜 ${parseYear}")
        return parseYear.toInt()
    }
    fun parseTimeToMonth():Int{
        val tmp = System.currentTimeMillis()
        val dateFormat= SimpleDateFormat("MM", Locale("En", "KR"))
        val parseMonth=dateFormat.format(tmp)
        Log.d(TAG,"현재 날짜 ${parseMonth}")
        return parseMonth.toInt()
    }

    fun parseTimeToEpisode():String{
        val tmp = System.currentTimeMillis()
        val dateFormat= SimpleDateFormat("YYYY-MM-dd", Locale("En", "KR"))
        var dayweek = checkDayWeek(Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
        val parseYear=dateFormat.format(tmp)
        Log.d(TAG,"현재 날짜 ${parseYear}")
        return "$parseYear ($dayweek)"
    }

    fun checkDayWeek(dayWeek:Int): String {
        return when(dayWeek){
            1 ->  "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else ->"일"
        }
    }
}