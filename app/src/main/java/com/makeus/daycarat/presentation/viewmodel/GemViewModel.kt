package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.base.BaseViewmodel
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.ActivityTag
import com.makeus.daycarat.data.data.EpisodeKeyword
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.data.GemTotalCount
import com.makeus.daycarat.domain.repository.GemRepository
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GemViewModel @Inject constructor(private val repository: GemRepository) :
    BaseViewmodel() {

    private val _gemTotalCount = MutableStateFlow<GemTotalCount>(GemTotalCount(0))
    val gemTotalCount = _gemTotalCount

    private val _gemTypeCount = MutableStateFlow<GemCount>(GemCount())
    val gemTypeCount = _gemTypeCount

    private val _gemMonthCount = MutableStateFlow<GemTotalCount>(GemTotalCount(0))
    val gemMonthCount = _gemMonthCount

    private val _mostKeyword = MutableStateFlow<EpisodeKeyword>(EpisodeKeyword(""))
    val mostKeyword = _mostKeyword

    private val _mostActivityTag = MutableStateFlow<ActivityTag>(ActivityTag(""))
    val mostActivityTag = _mostActivityTag

    init {
        flowTotalIndex = 5
    }

    fun initData(){
        sendEvent(UiEvent.LoadingEvent())
        getTypeGemCount()
        getTotalGemCount()
        getGemMonthCount()
        getMostActivity()
        getMostKeyword()
    }




    fun getTotalGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemTotalCount().collectLatest { result ->
                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _gemTotalCount.emit(it) }

            }
        }
    }
    fun getTypeGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemTypeCount().collectLatest { result ->
                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _gemTypeCount.emit(it) }

            }
        }
    }
    fun getGemMonthCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemMonthCount().collectLatest { result ->
                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _gemMonthCount.emit(it) }

            }
        }
    }
    fun getMostKeyword(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getMostKeyword().collectLatest { result ->
                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _mostKeyword.emit(it) }

            }
        }
    }

    fun getMostActivity(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getMostActivity().collectLatest { result ->
                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _mostActivityTag.emit(it) }

            }
        }
    }


}