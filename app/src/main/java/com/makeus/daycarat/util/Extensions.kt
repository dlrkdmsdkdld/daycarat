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

}