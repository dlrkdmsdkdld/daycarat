package com.makeus.daycarat.presentation.viewmodel.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeSeeViewModel @Inject constructor(private val repository: EpisodeRepository) :
    ViewModel() {

    private val _episode = MutableSharedFlow<EpisodeFullContent>()
    val episode: SharedFlow<EpisodeFullContent> = _episode

    fun getEpisode(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getEpisode(id).collect { result ->
                if (result.status == Status.SUCCESS) {
                    result.data?.let { _episode.emit(it) }
                }
            }
        }
    }


}