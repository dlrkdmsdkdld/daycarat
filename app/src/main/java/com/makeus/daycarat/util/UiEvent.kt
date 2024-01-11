package com.makeus.daycarat.util



sealed class UiEvent {
    class LoadingEvent(): UiEvent()
    class EndEvent():UiEvent()

//    class AdUiEvent(val categoryCode: String?, val type: String? , val isSkip:Boolean = false): UiEvent()
//    class FinishUiEvent(val finishWithAd: Boolean = false, val categoryCode: String?, val type: String?): UiEvent()
//    class DateUiEvent(val dateStr: String): UiEvent()
//    class ErrorUiEvent(): UiEvent()
//    class QuizProgressUiEvent(val totalCount: Int, val progressCount: Int): UiEvent()
//    class SkipUiEvent(val finishWithAd: Boolean = false, val categoryCode: String?, val type: String?): UiEvent()
}