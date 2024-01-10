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
        fun getTokenWithKakaoToken(kakaoAccessToken : String){
            viewModelScope.launch(Dispatchers.IO){
                repository(kakaoAccessToken).collect{data ->
                    when(data.status){
                        Status.LOADING ->{

                        }Status.SUCCESS ->{

                        }else ->{

                        }
                    }
                    if (data.status == Status.LOADING)
                    Log.d(Constant.TAG , "data ${data.status} accessToken ${data.data?.accessToken}")
                }
//                repository.invoke(kakaoAccessToken).collect{ data ->
//                    Log.d(Constant.TAG , "data ${data.status} accessToken ${data.data?.accessToken}")
//
//                }
            }
        }







}