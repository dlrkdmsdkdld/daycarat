package com.makeus.daycarat.presentation.viewmodel.episode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makeus.daycarat.DayCaratApplication
import com.makeus.daycarat.R
import com.makeus.daycarat.core.dto.Status
import com.makeus.daycarat.data.data.EpisodeContent
import com.makeus.daycarat.data.data.EpisodeRegister
import com.makeus.daycarat.data.data.EpisodeRegisterWithId
import com.makeus.daycarat.repository.EpisodeRepository
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.parseIntToMonth
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.TimeUtil.parseTimeToEpisode
import com.makeus.daycarat.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditEpisodeViewmodel @Inject constructor(private val repository: EpisodeRepository) :
    ViewModel() {


    private val _flowEvent = MutableSharedFlow<UiEvent>()
    val flowEvent = _flowEvent.asSharedFlow()

    private val _episode = MutableStateFlow<EpisodeRegister>(EpisodeRegister())
    val episode: StateFlow<EpisodeRegister> = _episode


    private val _episodeContent =
        MutableStateFlow<MutableList<EpisodeContent>>(mutableListOf())
    val episodeContent: StateFlow<MutableList<EpisodeContent>> = _episodeContent

    private val _editCount = MutableStateFlow<Int>(-1)

    private val _episodeDay = MutableStateFlow<String>(parseTimeToEpisode())
    val episodeDay: StateFlow<String> = _episodeDay

    private var episodeId:Int? = null

    var epiosdeContentTypeListIs = mutableListOf<Boolean>() // 유저가 선택한 데이터가 남아있는 변수
    var epiosdeContentTypeListPos = mutableListOf<Int>()// 유저가 선택한 데이터의 pos가 남아있는 변수 -몇번째스피너인지

    init {
        viewModelScope.launch(Dispatchers.IO) {
            DayCaratApplication.mAppContext?.resources?.getStringArray(R.array.episode_header_datas)
                ?.map { it.isNotEmpty() }?.toMutableList()?.let {
                epiosdeContentTypeListIs = it
            }
            epiosdeContentTypeListPos = epiosdeContentTypeListIs.map { 1000 }.toMutableList()
            Log.d(
                "GHLEE",
                " epiosdeContentTypeListIs $epiosdeContentTypeListIs listpos $epiosdeContentTypeListPos"
            )
        }


    }

    fun updateDay(date : String){
        viewModelScope.launch(Dispatchers.IO){
            _episodeDay.emit("${date}")
        }
    }

    fun updateDay(year:Int, month:Int,day:Int) {
        viewModelScope.launch(Dispatchers.IO){
            var parseMonth = month.parseIntToMonth()
            var parseDay = day.parseIntToMonth()
            _episodeDay.emit("${year}-$parseMonth-$parseDay")
        }
    }

    fun changeEpidoseContentText(pos: Int, text: String) {
        _episodeContent.value.getOrNull(pos)?.content = text
        Log.d(Constant.TAG, "pos $pos content ${_episodeContent.value.getOrNull(pos)?.content} ")
    }

    fun changeEpidoseContentType(pos: Int, text: String?) {
        _episodeContent.value.getOrNull(pos)?.episodeContentType = text ?: ""
        Log.d(
            Constant.TAG,
            "pos $pos  episodeContentType ${_episodeContent.value.getOrNull(pos)?.episodeContentType} "
        )
    }

    fun plusEditCount(): Int {
        _editCount.value = _editCount.value.plus(1)
        _episodeContent.value.add(EpisodeContent())
        return _editCount.value
    }

    fun clickSaveBtn(title: String, activityTag: String){
        episodeId?.let{
            updateEpisode(title , activityTag)
        }?: kotlin.run {
            registerEpisode(title , activityTag)
        }
    }

    fun updateEpisode(title: String, activityTag: String){
        viewModelScope.launch(Dispatchers.IO){
            var parseTitle = if (title.isEmpty()) "제목없음" else title
            repository.updateEpisode(
                EpisodeRegisterWithId(episodeId = this@EditEpisodeViewmodel.episodeId!!,
                title = parseTitle, selectedDate = _episodeDay.value,
                activityTag = activityTag, episodeContents = _episodeContent.value )
            )
                .collect { data ->
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

    fun registerEpisode(title: String, activityTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var parseTitle = if (title.isEmpty()) "제목없음" else title
            if (activityTag.isNotEmpty()) SharedPreferenceManager.getInstance().saveEpisodeActivityTag(activityTag)
            repository.addEpisode(
                EpisodeRegister(title = parseTitle, date = _episodeDay.value, activityTag = activityTag, episodeContents = _episodeContent.value)
            )
                .collect { data ->
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

//            var title:String ="" , var date:String ="" ,var activityTag:String ="" , var episodeContents:List<EpisodeContent> = listOf()
        }
    }

    fun userSelectSpinner(pos: Int) {
        epiosdeContentTypeListIs[pos] = false
    }

    fun userSelectSaveLastSpinner(spinnerPos: Int, selectPos: Int) { // 스피너 배열의 pos
        epiosdeContentTypeListPos[spinnerPos] = selectPos
    }


    fun userUnSelectSpinner(spinnerPos: Int) {
        epiosdeContentTypeListPos.getOrNull(spinnerPos)?.let { lastPos ->
            epiosdeContentTypeListIs.getOrNull(lastPos)?.let {
                epiosdeContentTypeListIs[lastPos] = true
            }
        }
    }

    private fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _flowEvent.emit(event)
        }
    }

    fun setEditMode(episodeId:Int){
        this.episodeId = episodeId
    }
}