package com.makeus.daycarat

import android.app.Application
import android.content.Context
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DayCaratApplication : Application(){
    companion object{
        var mAppContext:Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
        KakaoSdk.init(this, "945e8ef4af6e12c87d1f13a8b4308cf7")
    }


}