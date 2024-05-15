package com.makeus.daycarat.presentation.viewmodel.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.repository.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailTypeViewModel @Inject constructor(private val repository: EpisodeRepository) : ViewModel() {

    private val _episodeContentList = MutableSharedFlow<PagingData<EpisodeDetailContent>>()
    val episodeContentList: SharedFlow<PagingData<EpisodeDetailContent>> = _episodeContentList

    var year:Int = 0
    var typeItem: EpisodeActivityCounter? = null

    fun startPaging(tmpYear : Int ,tmptypeItem : EpisodeActivityCounter){
        typeItem = tmptypeItem
        year = tmpYear
        if (typeItem?.activityTagName == null  ){
            getPagingEpisodeContentOrderByDate(year , typeItem!!.month)
        }else{
            getPagingEpisodeContentOrderByCount(typeItem?.activityTagName!!)
        }
    }

    fun removeEpisodeEvent(): Int? {
        typeItem?.quantity?.let {
            typeItem?.quantity = typeItem?.quantity?.minus(1)!!
        }
        return typeItem?.quantity
    }


    fun getPagingEpisodeContentOrderByDate(year:Int , month:Int){
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByDatePaging(month = month, year = year ).cachedIn(viewModelScope).collect{
                _episodeContentList.emit(it)
            }
        }
    }

    fun getPagingEpisodeContentOrderByCount(activityTag:String){
        viewModelScope.launch(Dispatchers.IO){
            repository.getContentEpisodeByCountPaging(activityTag).cachedIn(viewModelScope).collect{
                _episodeContentList.emit(it)
            }
        }
    }



}