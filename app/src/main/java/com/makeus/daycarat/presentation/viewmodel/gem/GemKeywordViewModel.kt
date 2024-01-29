package com.makeus.daycarat.presentation.viewmodel.gem

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.data.EpisodeKeywordAndId
import com.makeus.daycarat.data.SoaraContent
import com.makeus.daycarat.presentation.viewmodel.AuthViewmodel
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.repository.GemRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GemKeywordViewModel  @AssistedInject constructor(@Assisted initData:EpisodeKeywordAndId , // 초기화데이터 주입받지않고하기
                                                       val episdoeRepository: EpisodeRepository) : ViewModel() {


    @AssistedFactory
    interface GemKeywordViewModelFactory {
        fun create(initData: EpisodeKeywordAndId): GemKeywordViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: GemKeywordViewModelFactory,
            initData: EpisodeKeywordAndId
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(initData) as T
            }
        }
    }


    private val _flowEvent = MutableSharedFlow<AuthViewmodel.UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()


    private fun sendEvent(event: AuthViewmodel.UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
    }

    private val _userSelectKeyword = MutableStateFlow<EpisodeKeywordAndId>(initData)
    val userSelectKeyword: StateFlow<EpisodeKeywordAndId> = _userSelectKeyword


    fun updateKeyword(keyword:String){
        viewModelScope.launch(Dispatchers.IO){
            Log.d("GHLESS","old ${_userSelectKeyword.value.keyword} new keyword")
            _userSelectKeyword.emit(EpisodeKeywordAndId(keyword ,_userSelectKeyword.value.episodeId ) )
        }
    }


}