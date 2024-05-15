package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.data.data.EpisodeCount
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewmodel @Inject constructor(private val repository: EpisodeRepository) : ViewModel() {

    private val _episodeCountList =
        MutableStateFlow<List<EpisodeActivityCounter>>(listOf<EpisodeActivityCounter>())
    val episodeCountList: StateFlow<List<EpisodeActivityCounter>> = _episodeCountList

    var selectYear = 2024

    fun getActivityTagOderByCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getActivityTagOderByCount().collect{ result ->
                if (result.status == Status.SUCCESS){
                    result.data?.let {
                        _episodeCountList.emit(it)
                        it.forEach {
                            Log.d("GHLEE","it ${it.activityTagName} month ${it.month}quantity  ${it.quantity}")
                        }
                    }
                }


            }
        }
    }
    fun getActivityTagOderByDate(year:Int = 2024){
        viewModelScope.launch(Dispatchers.IO){
            repository.getActivityTagOderByDate(year).collect{ result ->
                if (result.status == Status.SUCCESS){
                    result.data?.let {
                        selectYear = year
                        _episodeCountList.emit(it)
                        it.forEach {
                            Log.d("GHLEE","it ${it.activityTagName} month ${it.month}quantity  ${it.quantity}")
                        }
                    }
                }


            }
        }
    }

    private val _episodeTotalCount = MutableStateFlow<EpisodeCount>(EpisodeCount(0))
    val episodeTotalCount = _episodeTotalCount

    fun getTotalGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getTotalEpisodeCount().collectLatest { result ->
//                if (result.status != Status.LOADING) finishNowFlow()
                result.data?.let { _episodeTotalCount.emit(it) }

            }
        }
    }

}