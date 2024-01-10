package com.makeus.daycarat.data

import com.google.gson.annotations.SerializedName

data class UserData(
    var nickname: String ="",
    var jobTitle: String ="",
    var strength: String = "",
    var pushAllow: Boolean = true,
    )