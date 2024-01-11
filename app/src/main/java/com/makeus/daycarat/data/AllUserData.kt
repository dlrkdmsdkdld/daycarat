package com.makeus.daycarat.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class AllUserData (
    var nickname: String ="",
    var jobTitle: String ="",
    var strength: String = "",
    var pushAllow: Boolean = true,
    var email: String ="",
    var profileImage: String =""
    )