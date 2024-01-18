package com.makeus.daycarat.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.format.DateUtils
import com.makeus.daycarat.DayCaratApplication
import org.json.JSONArray
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


    fun saveEpisodeActivityTag(newInput:String){
        var sharedDatas = prefs.getString(Constant.USER_EPISODE_TAGS, "")
        var arrJosn = if (sharedDatas!!.isNotEmpty()) JSONArray(sharedDatas) else JSONArray()
        arrJosn.put(newInput)
        prefs.edit().putString(Constant.USER_EPISODE_TAGS,arrJosn.toString()).commit()

    }

    fun getEpisodeActivityTags(): ArrayList<String> {
        var sharedDatas = prefs.getString(Constant.USER_EPISODE_TAGS, "")
        var arrJosn = JSONArray(sharedDatas)
        var resultArr : ArrayList<String> = ArrayList()
        for (i in 0 until arrJosn.length()){
            resultArr.add(arrJosn.optString(i))
        }
        return resultArr
    }

}