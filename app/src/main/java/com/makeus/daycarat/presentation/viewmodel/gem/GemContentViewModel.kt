package com.makeus.daycarat.presentation.viewmodel.gem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.EpisodeContent
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.data.data.GemSoaraAIContent
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.repository.GemRepository
import com.makeus.daycarat.util.UiEvent
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

    private val _flowAIKeywordEvent = MutableSharedFlow<UiEvent>()
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

    private val _AISoara = MutableStateFlow<GemSoaraAIContent>(GemSoaraAIContent())
    val AISoara: StateFlow<GemSoaraAIContent> = _AISoara

    var episodeId: Int = 0
    var keyword: String = ""

    fun inputEpsodeId(episodeId: Int, keyword: String) {
        this.episodeId = episodeId
        this.keyword = keyword
        getSoara(this.episodeId)
        getEpisode(this.episodeId)
        getAiSoara()
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

    fun getAiSoara() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAISoara(episodeId).collectLatest { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        data.data?.let { it1 -> _AISoara.emit(it1 as GemSoaraAIContent) }
                        sendAIEvent(UiEvent.SuccessEvent())
                    }

                    Status.WORKING -> {
                        sendAIEvent(UiEvent.WorkingEvent())
                    }

                    Status.SERVER_FAIL -> {
                        sendAIEvent(UiEvent.ServerFailEvent())

                    }

                    Status.ERROR -> {
                        sendAIEvent(UiEvent.FailEvent(data.message))
                    }

                    else -> {}
                }
            }
        }
    }

    fun getCopyString() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCopyString(episodeId).collectLatest { data ->
                when (data.status) {
                    Status.SUCCESS -> {
                        _flowCopyEvent.emit(UiEvent.CopyEvent(data.data?.content))
                    }

                    else -> {}
                }

            }
        }

    }

    private fun sendAIEvent(event: UiEvent) {
        viewModelScope.launch {
            _flowAIKeywordEvent.emit(event)
        }
    }

    private val _flowCopyEvent = MutableSharedFlow<UiEvent>()
    val flowCopyEvent = _flowCopyEvent.asSharedFlow()


}