package com.makeus.daycarat.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailTypeViewModel @Inject constructor(private val repository: EpisodeRepository) : ViewModel() {

    val episodeContents = repository.getContentEpisodeByDatePaging(month = 1, year = 2024 ).cachedIn(viewModelScope)


}