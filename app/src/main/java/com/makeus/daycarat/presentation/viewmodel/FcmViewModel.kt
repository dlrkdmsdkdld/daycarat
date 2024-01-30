package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.base.BaseViewmodel
import com.makeus.daycarat.repository.AuthRepository
import com.makeus.daycarat.repository.FcmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FcmViewModel @Inject constructor(private val repository: FcmRepository)
    : BaseViewmodel() {

        fun updateFCMToken(fcmToken:String){
            viewModelScope.launch(Dispatchers.IO) {
                repository(fcmToken).collectLatest {

                }
            }
        }

}