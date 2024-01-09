package com.makeus.daycarat.util

import android.content.res.Resources
import android.util.TypedValue
import com.makeus.daycarat.DayCaratApplication

object UiManager {
    fun getPixel(dp: Int): Int {
//        Resources r = ((BitApplication)BitApplication.getInstance()).getLocalizedResources();
        val r: Resources = DayCaratApplication.mAppContext!!.getResources()
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), r.displayMetrics)
            .toInt()
    }
}

