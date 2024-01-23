package com.makeus.daycarat.data

import android.os.Parcel
import android.os.Parcelable


data class EpisodeActivityCounter (
    var activityTagName : String?="" ,
    var month:Int = 0,
    var quantity:Int):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(activityTagName)
        parcel.writeInt(month)
        parcel.writeInt(quantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EpisodeActivityCounter> {
        override fun createFromParcel(parcel: Parcel): EpisodeActivityCounter {
            return EpisodeActivityCounter(parcel)
        }

        override fun newArray(size: Int): Array<EpisodeActivityCounter?> {
            return arrayOfNulls(size)
        }
    }

}