package com.makeus.daycarat.data.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeFullContent(
    var episodeId :Int,
    val title:String,
    val activityTagName:String,
    val selectedDate:String,
    val episodeState:String,
    val episodeContents:List<EpisodeContent>,

    ) : Parcelable {

}