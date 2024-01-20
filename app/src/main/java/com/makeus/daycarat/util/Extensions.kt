package com.makeus.daycarat.util

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
}