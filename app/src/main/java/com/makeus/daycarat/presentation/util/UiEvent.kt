package com.makeus.daycarat.util

import com.makeus.daycarat.data.data.EpisodeKeywordAndId


sealed class UiEvent {

    class LoadingEvent(): UiEvent()
    class AlreadyUserEvent(): UiEvent()
    class NewUserEvent(): UiEvent()
    class FailEvent(val message:String? =""): UiEvent()
    class SuccessEvent(): UiEvent()

    class WorkingEvent(): UiEvent()
    class ServerFailEvent(): UiEvent()

    class CopyEvent(val copyData:String? =""): UiEvent()

    class SuccessUpdateKeywordEvent(val result : EpisodeKeywordAndId): UiEvent()

    class FinishLoading(): UiEvent()

}
