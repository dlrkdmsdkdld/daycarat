package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.repository.AuthRepository
import com.makeus.daycarat.repository.UserInfoRepository
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewmodel @Inject constructor(private val repository: UserInfoRepository) : ViewModel(){
    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData: StateFlow<UserData> = _userData

    private val _flowEvent = MutableSharedFlow<UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    fun updateUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserData(_userData.value).collect{result ->
                when(result.status) {
                    Status.LOADING -> {
                        sendEvent(UiEvent.LoadingEvent())
                    }

                    Status.SUCCESS -> {
                        sendEvent(UiEvent.SuccessEvent())
                    }
                    else -> {

                        sendEvent(UiEvent.FailEvent(result.message))

                    }
                }

            }
        }
    }
    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
//        _flowEvent
    }



}