package com.makeus.daycarat.presentation.viewmodel.gem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.paging.GemDetailConetent
import com.makeus.daycarat.repository.GemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GemDetailViewModel @Inject constructor(private val repository: GemRepository) : ViewModel() {

    private val _gemList = MutableSharedFlow<PagingData<GemDetailConetent>>()
    val gemList: SharedFlow<PagingData<GemDetailConetent>> = _gemList.asSharedFlow()

    var keyword :String =""
    var itemCount :Int = 0

    fun startPaging(keyword: String, itemCount: Int){
        this.keyword = keyword
        this.itemCount = itemCount
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByDatePaging(this@GemDetailViewModel.keyword).cachedIn(viewModelScope).collect{
                _gemList.emit(it)
            }
        }
        getTypeGemCount()
    }

    private val _gemTypeCount = MutableStateFlow<GemCount>(GemCount())
    val gemTypeCount = _gemTypeCount

    fun getTypeGemCount(){
        viewModelScope.launch(Dispatchers.IO){
            repository.getGemTpyeCount().collectLatest { result ->
                result.data?.let { _gemTypeCount.emit(it) }

            }
        }
    }


}


