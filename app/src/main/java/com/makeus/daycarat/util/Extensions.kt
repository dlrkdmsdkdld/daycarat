package com.makeus.daycarat.util

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object Extensions {
    fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
//        lifecycleScope.launchWhenStarted {
//        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED, block)
        }

    }

    fun String?.isJsonObject():Boolean {
        return this?.startsWith("{" ) == true &&this.endsWith("}")
        //return this?.startsWith("{" ) == true &&this.endsWith("}")
    }
    fun String?.isJsonArray():Boolean{
        return this?.startsWith("[" ) == true &&this.endsWith("]")
    }

    fun Int?.parseIntToMonth():String{
        val month:Int?=this
        var parseminute = month.toString()
        if (month!!<10){
            parseminute="0$month"
        }
        return parseminute
    }
    fun Activity.setStatusBarTransparent() {
        window.apply {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
        if(Build.VERSION.SDK_INT >= 30) {	// API 30 에 적용
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
    }

}