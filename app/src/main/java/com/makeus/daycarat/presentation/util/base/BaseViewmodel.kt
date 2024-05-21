package com.makeus.daycarat.presentation.util.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.presentation.util.UiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewmodel  :ViewModel() {

    private val _flowEvent = MutableSharedFlow<UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
    }

    var flowTotalIndex = 0 // 각 뷰모델마당세팅
    var flowCurrent = 0

    fun finishNowFlow(){
        flowCurrent+=1
        if (flowCurrent == flowTotalIndex){
            sendEvent(UiEvent.FinishLoading())
//            flowCurrent = 0 굳이안해도될듯
        }
    }


}