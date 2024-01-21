package com.makeus.daycarat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewmodel @Inject constructor(private val repository: EpisodeRepository) : ViewModel() {

    private val _episodeCountList =
        MutableStateFlow<List<EpisodeActivityCounter>>(listOf<EpisodeActivityCounter>())
    val episodeCountList: StateFlow<List<EpisodeActivityCounter>> = _episodeCountList

    init {
        getActivityTagOderByCount()
    }

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

}