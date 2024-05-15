package com.makeus.daycarat.data.data

import com.google.gson.annotations.SerializedName

data class UserData(
    var nickname: String? =null,
    var jobTitle: String? =null,
    var strength: String? =null,
    var pushAllow: Boolean = true,
    var fcmToken:String? =null
    )