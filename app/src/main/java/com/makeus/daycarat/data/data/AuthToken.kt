package com.makeus.daycarat.data.data

import com.google.gson.annotations.SerializedName

data class AuthToken(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)