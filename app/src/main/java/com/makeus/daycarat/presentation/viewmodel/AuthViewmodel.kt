package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.domain.repository.AuthRepository
import com.makeus.daycarat.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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





}