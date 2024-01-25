package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class SoaraViewmodel @Inject constructor(private val repository: EpisodeRepository) :
    ViewModel() {

    private val _episodeConetent = MutableStateFlow<EpisodeFullContent>(EpisodeFullContent(0,"","","","", listOf<EpisodeContent>()))
    val episodeConetent: StateFlow<EpisodeFullContent> = _episodeConetent

    fun setEpisode(data : EpisodeFullContent){
        Log.d("GHLEESS","setEpisode")
        viewModelScope.launch {
            _episodeConetent.emit(data)
        }
    }

}