package com.makeus.daycarat.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.format.DateUtils
import com.makeus.daycarat.DayCaratApplication
import java.util.Calendar


class SharedPreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("day_carat", Context.MODE_PRIVATE)

    companion object{
        var sharedPreferenceManager: SharedPreferenceManager? = null
        fun getInstance(): SharedPreferenceManager {
            if (sharedPreferenceManager == null) {
                sharedPreferenceManager = SharedPreferenceManager(DayCaratApplication.mAppContext!!)
            }
            return sharedPreferenceManager!!
        }
    }

    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).commit()
    }

    fun getInt(key: String, defValue: Int): Int = prefs.getInt(key, defValue)

    fun setInt(key: String, num: Int) {
        prefs.edit().putInt(key, num).commit()
    }
}