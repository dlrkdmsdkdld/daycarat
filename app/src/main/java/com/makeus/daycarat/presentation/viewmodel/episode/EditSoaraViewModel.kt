package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.data.SoaraContent
import com.makeus.daycarat.data.setContent
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditSoaraViewModel @Inject constructor(private val repository: GemRepository) :
    ViewModel() {


    private val _episodeTemp = MutableStateFlow<EpisodeTempData>(EpisodeTempData())
    val episodeTemp: StateFlow<EpisodeTempData> = _episodeTemp

    private val _flowEvent = MutableSharedFlow<AuthViewmodel.UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()


    fun initEpisodeData(episodeId: Int, episodeConetent: String, contentNum: Int) {
        viewModelScope.launch {
            _episodeTemp.emit(EpisodeTempData(episodeId, episodeConetent, contentNum))
        }
    }

    fun editContent(data: String) {
        viewModelScope.launch {
            _episodeTemp.emit(
                EpisodeTempData(
                    _episodeTemp.value.episodeId,
                    data,
                    _episodeTemp.value.contentNum
                )
            )
        }
    }

    fun setSoara() {
        viewModelScope.launch(Dispatchers.IO) {
            var data = SoaraContent(
                _episodeTemp.value.episodeId,
                null
            ).setContent(
                _episodeTemp.value.episodeId,
                _episodeTemp.value.contentNum,
                _episodeTemp.value.episodeConetent
            )
            repository.setSoaraContent(data).collect { data ->
                when (data.status) {
                    Status.LOADING -> {
                        sendEvent(AuthViewmodel.UiEvent.LoadingEvent())
                    }

                    Status.SUCCESS -> {
                        sendEvent(AuthViewmodel.UiEvent.SuccessEvent())
                    }

                    else -> {
                        sendEvent(AuthViewmodel.UiEvent.FailEvent())
                    }
                }
            }
        }
    }


    inner class EpisodeTempData(
        var episodeId: Int = 0,
        var episodeConetent: String = "",
        var contentNum: Int = 0
    ) {

    }

    private fun sendEvent(event: AuthViewmodel.UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
    }

}