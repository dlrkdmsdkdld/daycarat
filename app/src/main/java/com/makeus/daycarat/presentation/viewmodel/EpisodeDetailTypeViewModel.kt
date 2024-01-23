package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailTypeViewModel @Inject constructor(private val repository: EpisodeRepository) : ViewModel() {

    private val _episodeContentList = MutableSharedFlow<PagingData<EpisodeDetailContent>>()
    val episodeContentList: SharedFlow<PagingData<EpisodeDetailContent>> = _episodeContentList

    fun getPagingEpisodeContentOrderByDate(year:Int , month:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByDatePaging(month = month, year = year ).cachedIn(viewModelScope).collect{
                _episodeContentList.emit(it)
            }
        }
    }

    fun getPagingEpisodeContentOrderByCount(activityTag:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByCountPaging(activityTag).cachedIn(viewModelScope).collect{
                _episodeContentList.emit(it)
            }
        }
    }



}