package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.EpisodeContent
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoaraViewmodel @Inject constructor(private val repository: GemRepository) :
    ViewModel() {

    private val _episodeConetent = MutableStateFlow<EpisodeFullContent>(EpisodeFullContent(0,"","","","", listOf<EpisodeContent>()))
    val episodeConetent: StateFlow<EpisodeFullContent> = _episodeConetent

    private val _episodeSoara = MutableStateFlow<SoaraContent>(SoaraContent(null , null))
    val episodeSoara: StateFlow<SoaraContent> = _episodeSoara

    fun setEpisode(data : EpisodeFullContent){
        Log.d("GHLEESS","setEpisode")
        viewModelScope.launch(Dispatchers.IO) {
            _episodeConetent.emit(data)
            getSoara(data.episodeId)
        }
    }

    fun getSoara(episodeId :Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSoaraContent(episodeId).collectLatest { result ->
                if (result.status == Status.SUCCESS){
                    result.data?.let {
                        _episodeSoara.emit(it)
                    }
                }
            }
        }

    }


}