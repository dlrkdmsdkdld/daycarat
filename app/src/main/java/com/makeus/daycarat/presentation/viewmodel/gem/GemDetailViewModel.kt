package com.makeus.daycarat.presentation.viewmodel.gem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeus.daycarat.data.EpisodeActivityCounter
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.data.paging.GemDetailConetent
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GemDetailViewModel @Inject constructor(private val repository: GemRepository) : ViewModel() {

    private val _gemList = MutableSharedFlow<PagingData<GemDetailConetent>>()
    val gemList: SharedFlow<PagingData<GemDetailConetent>> = _gemList.asSharedFlow()

    var keyword :String =""

    fun startPaging(keyword:String){
        this.keyword = keyword
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByDatePaging(this@GemDetailViewModel.keyword).cachedIn(viewModelScope).collect{
                _gemList.emit(it)
            }
        }
    }


}


