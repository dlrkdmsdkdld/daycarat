package com.makeus.daycarat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.data.ActivityTag
import com.makeus.daycarat.data.EpisodeKeyword
import com.makeus.daycarat.data.GemCount
import com.makeus.daycarat.data.GemTotalCount
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GemViewModel @Inject constructor(private val repository: GemRepository) :
    ViewModel() {

    private val _gemTotalCount = MutableStateFlow<GemTotalCount>(GemTotalCount(0))
    val gemTotalCount = _gemTotalCount

    private val _gemTypeCount = MutableStateFlow<GemCount>(GemCount())
    val gemTypeCount = _gemTypeCount.asSharedFlow()

    private val _gemMonthCount = MutableStateFlow<GemTotalCount>(GemTotalCount(0))
    val gemMonthCount = _gemMonthCount

    private val _mostKeyword = MutableStateFlow<EpisodeKeyword>(EpisodeKeyword(""))
    val mostKeyword = _mostKeyword

    private val _mostActivityTag = MutableStateFlow<ActivityTag>(ActivityTag(""))
    val mostActivityTag = _mostActivityTag

    init {
        getTypeGemCount()
        getTotalGemCount()
        getGemMonthCount()
        getMostActivity()
        getMostKeyword()
    }
    fun getTotalGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemTotalCount().collectLatest { result ->
                result.data?.let { _gemTotalCount.emit(it) }

            }
        }
    }
    fun getTypeGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemTpyeCount().collectLatest { result ->
                result.data?.let { _gemTypeCount.emit(it) }

            }
        }
    }
    fun getGemMonthCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemMonthCount().collectLatest { result ->
                result.data?.let { _gemMonthCount.emit(it) }

            }
        }
    }
    fun getMostKeyword(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getMostKeyword().collectLatest { result ->
                result.data?.let { _mostKeyword.emit(it) }

            }
        }
    }

    fun getMostActivity(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getMostActivity().collectLatest { result ->
                result.data?.let { _mostActivityTag.emit(it) }

            }
        }
    }


}