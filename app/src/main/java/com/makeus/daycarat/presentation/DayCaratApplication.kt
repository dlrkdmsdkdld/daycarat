package com.makeus.daycarat.presentation

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.KakaoSdk
import com.makeus.daycarat.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DayCaratApplication : Application(){
    companion object{
        var mAppContext:Context? = null
    }
    override fun onCreate() {
        super.onCreate()
        mAppContext = applicationContext
        FirebaseApp.initializeApp(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        //        CLog.ignoreList();
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }


}