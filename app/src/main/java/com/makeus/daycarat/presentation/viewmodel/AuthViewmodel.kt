package com.makeus.daycarat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.repository.AuthRepository
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.SharedPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthViewmodel @Inject constructor(private val repository: AuthRepository) : ViewModel(){
    private val _flowEvent = MutableSharedFlow<UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

        fun getTokenWithKakaoToken(kakaoAccessToken : String){
            viewModelScope.launch(Dispatchers.IO){
                repository(kakaoAccessToken).collect{data ->
                    when(data.status){
                        Status.LOADING ->{
                            sendEvent(UiEvent.LoadingEvent())
                        }Status.SUCCESS ->{
                            if (data.data == 201){
                                sendEvent(UiEvent.NewUserEvent())
                            }else{
                                sendEvent(UiEvent.AlreadyUserEvent())
                            }
                        }else ->{
                          sendEvent(UiEvent.FailEvent())
                        }
                    }
                }
//                repository.invoke(kakaoAccessToken).collect{ data ->
//                    Log.d(Constant.TAG , "data ${data.status} accessToken ${data.data?.accessToken}")
//
//                }
            }
        }
    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
    }
    sealed class UiEvent {

        class LoadingEvent(): UiEvent()
        class AlreadyUserEvent(): UiEvent()
        class NewUserEvent(): UiEvent()
        class FailEvent(val message:String? =""): UiEvent()
        class SuccessEvent(): UiEvent()
    }





}