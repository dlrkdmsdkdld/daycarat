package com.makeus.daycarat.presentation.viewmodel.gem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeContent
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.data.SoaraContent
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.repository.EpisodeRepository
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
class GemContentViewModel @Inject constructor(
    private val repository: GemRepository,
    private val episdoeRepository: EpisodeRepository
) :
    ViewModel() {

    private val _flowAIKeywordEvent = MutableSharedFlow<AuthViewmodel.UiEvent>()
    val flowAIKeywordEvent = _flowAIKeywordEvent.asSharedFlow()


    private val _episodeConetent = MutableStateFlow<EpisodeFullContent>(
        EpisodeFullContent(
            0,
            "",
            "",
            "",
            "",
            listOf<EpisodeContent>()
        )
    )
    val episodeConetent: StateFlow<EpisodeFullContent> = _episodeConetent

    private val _episodeSoara = MutableStateFlow<SoaraContent>(SoaraContent(null, null))
    val episodeSoara: StateFlow<SoaraContent> = _episodeSoara

    var episodeId: Int = 0

    fun inputEpsodeId(episodeId: Int) {
        this.episodeId = episodeId
        getSoara(this.episodeId)
        getEpisode(this.episodeId)
    }


    fun getSoara(episodeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getSoaraContent(episodeId).collectLatest { result ->
//                sendKeywordEvent(result.status)
                result.data?.let {
                    _episodeSoara.emit(it)
                }
            }
        }

    }

    fun getEpisode(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            episdoeRepository.getEpisode(id).collect { result ->
                if (result.status == Status.SUCCESS) {
                    result.data?.let { _episodeConetent.emit(it) }
                }
            }
        }
    }

//    private fun sendKeywordEvent(event: Status) {
//        viewModelScope.launch {
//            if (event.)
//            _flowEvent.emit(event)
//        }
//    }


}