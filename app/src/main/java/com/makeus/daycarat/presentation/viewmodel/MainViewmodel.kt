package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.AllUserData
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
class MainViewmodel @Inject constructor(private val repository: UserInfoRepository) : ViewModel(){
    private val _userData = MutableStateFlow<AllUserData>(AllUserData())
    val userData: StateFlow<AllUserData> = _userData

    private val _flowEvent = MutableSharedFlow<UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    init {
        getUserInfo()
    }

    fun getUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserData().collect{result ->
                when(result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { data ->
                            _userData.emit(data)
                        }
                    }
                    else -> {

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