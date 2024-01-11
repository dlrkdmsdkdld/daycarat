package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.repository.AuthRepository
import com.makeus.daycarat.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDataViewmodel @Inject constructor(private val repository: UserInfoRepository) : ViewModel(){
    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData: StateFlow<UserData> = _userData

    fun updateUserInfo(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUserData(_userData.value).collect{result ->
                when(result.status) {
                    Status.LOADING -> {

                    }

                    Status.SUCCESS -> {

                    }

                    else -> {

                    }
                }

            }
        }
    }




}