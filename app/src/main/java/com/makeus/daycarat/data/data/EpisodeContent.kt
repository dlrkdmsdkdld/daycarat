package com.makeus.daycarat.data.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeContent(
    var episodeContentType: String ="",
    var content:String =""): Parcelable {

}