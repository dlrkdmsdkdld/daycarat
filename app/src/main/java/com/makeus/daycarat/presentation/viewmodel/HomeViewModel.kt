package com.makeus.daycarat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeRecent
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.repository.AuthRepository
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.repository.UserInfoRepository
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil.parseTimeToMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: EpisodeRepository) : ViewModel(){

    private val _flowEvent = MutableSharedFlow<AuthViewmodel.UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    private val _episodeCount = MutableStateFlow<Int>(0)
    val episodeCount: StateFlow<Int> = _episodeCount

    private val _recentEpisode = MutableStateFlow<List<EpisodeRecent>>(listOf())
    val recentEpisode: StateFlow<List<EpisodeRecent>> = _recentEpisode

    init {

    }

    fun updateUserInfo(){
        Log.d(Constant.TAG ,"updateUserInfo")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserMontlyEpisodeCount().collect{result ->
                if (result.data?.isNotEmpty() == true){
                    result.data.forEach {
                        if (parseTimeToMonth() == it.month){
                            _episodeCount.emit(it.quantity)
//                            _episodeCount.postValue(it.quantity)
                        }
                    }
                }

            }
        }
    }

    fun getRecentEpisode(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getRecentEpisode().collect{result ->
                if (result.status == Status.SUCCESS){
                    result.data?.let{_recentEpisode.emit(result.data)}
                }

            }
        }
    }


    private fun sendEvent(event: AuthViewmodel.UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
//        _flowEvent
    }



}