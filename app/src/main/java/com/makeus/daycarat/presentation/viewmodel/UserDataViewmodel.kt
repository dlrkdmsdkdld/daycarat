package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.makeus.daycarat.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class UserDataViewmodel : ViewModel(){
    private val _userData = MutableStateFlow<UserData>(UserData())
    val userData: StateFlow<UserData> = _userData




}