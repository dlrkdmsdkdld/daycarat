package com.makeus.daycarat.data.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EpisodeActivityCounter (
    var activityTagName : String? ,
    var month:Int = 0,
    var quantity:Int) :Parcelable{


}
