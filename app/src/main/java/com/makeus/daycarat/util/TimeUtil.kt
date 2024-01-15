package com.makeus.daycarat.util

import android.util.Log
import com.makeus.daycarat.util.Constant.TAG
import java.text.SimpleDateFormat
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
}