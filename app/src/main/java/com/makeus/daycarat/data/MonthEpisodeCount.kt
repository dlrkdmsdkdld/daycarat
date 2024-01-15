package com.makeus.daycarat.data

import com.google.gson.annotations.SerializedName

data class MonthEpisodeCount(
    @SerializedName("month")
    val month: Int,
    @SerializedName("quantity")
    val quantity: Int
)