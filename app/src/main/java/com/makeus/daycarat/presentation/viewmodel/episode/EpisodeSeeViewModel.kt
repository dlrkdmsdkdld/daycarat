package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeSeeViewModel @Inject constructor(private val repository: EpisodeRepository) :
    ViewModel() {


    private val _episodeConetent = MutableStateFlow<EpisodeFullContent>(EpisodeFullContent(0,"","","","", listOf<EpisodeContent>()))
    val episodeConetent: SharedFlow<EpisodeFullContent> = _episodeConetent


    private val _episodeId = MutableStateFlow<Int>(0)
    val episodeId: StateFlow<Int> = _episodeId

    fun getEpisode(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEpisode(id).collect { result ->
                if (result.status == Status.SUCCESS) {
                    result.data?.let { _episodeConetent.emit(it) }
                }
            }
        }
    }
    fun getEpisodeContent(): EpisodeFullContent {
        return _episodeConetent.value
    }


}