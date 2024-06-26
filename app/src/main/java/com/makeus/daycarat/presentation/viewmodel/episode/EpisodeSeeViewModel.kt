package com.makeus.daycarat.presentation.viewmodel.episode

import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.presentation.util.base.BaseViewmodel
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.EpisodeContent
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeSeeViewModel @Inject constructor(private val repository: EpisodeRepository) :
    BaseViewmodel() {


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

    fun deleteEpisode() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteEpisode(_episodeConetent.value.episodeId).collect { data ->
                when (data.status) {
                    Status.LOADING -> {
                        sendEvent(UiEvent.LoadingEvent())
                    }
                    Status.SUCCESS -> {
                        sendEvent(UiEvent.SuccessEvent())
                    }

                    else -> {
                        sendEvent(UiEvent.FailEvent())
                    }
                }
            }
        }
    }


}