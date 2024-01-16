package com.makeus.daycarat.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEpisodeViewmodel @Inject constructor(private val repository: EpisodeRepository) : ViewModel(){


    private val _flowEvent = MutableSharedFlow<AuthViewmodel.UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    private val _episode = MutableStateFlow<EpisodeRegister>(EpisodeRegister())
    val episode: StateFlow<EpisodeRegister> = _episode


    private val _episodeContent = MutableStateFlow<MutableList<EpisodeContent>>(mutableListOf(EpisodeContent()))
    val episodeContent: StateFlow<MutableList<EpisodeContent>> = _episodeContent

    private val _editCount = MutableStateFlow<Int>(0)
    val editCount: StateFlow<Int> = _editCount


    fun changeEpidoseContentText(pos:Int,text:String){
        _episodeContent.value.getOrNull(pos)?.content = text
        Log.d(Constant.TAG , "pos $pos content ${_episodeContent.value.getOrNull(pos)?.content} ")
    }

    fun changeEpidoseContentType(pos:Int,text:String?){
        _episodeContent.value.getOrNull(pos)?.episodeContentType = text?:""
        Log.d(Constant.TAG , "pos $pos  episodeContentType ${_episodeContent.value.getOrNull(pos)?.episodeContentType} ")
    }

    fun plusEditCount(): Int {
        _editCount.value = _editCount.value.plus(1)
        _episodeContent.value.add(EpisodeContent())
        return _editCount.value
    }

}