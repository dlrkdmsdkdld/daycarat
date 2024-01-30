package com.makeus.daycarat

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
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
        FirebaseApp.initializeApp(this)
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
        //        CLog.ignoreList();
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }


}