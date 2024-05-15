package com.makeus.daycarat.presentation.viewmodel.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompleteSoaraViewmodel @Inject constructor(private val repository: GemRepository) :
    ViewModel() {

        fun register(episodeId: Int){
            viewModelScope.launch(Dispatchers.IO){
                repository.completeSoara(episodeId).collectLatest {

                }

            }
        }


    }
