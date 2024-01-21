package com.makeus.daycarat

import android.app.Application
import android.content.Context
import com.google.firebase.crashlytics.FirebaseCrashlytics
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
        KakaoSdk.init(this, "de909f10bb5f9eea10806f013137db09")
        //        CLog.ignoreList();
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }


}