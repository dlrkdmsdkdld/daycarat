package com.makeus.daycarat.data.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SoaraContent(
    @SerializedName("episodeId")
    var episodeId: Int?,
    @SerializedName("gemId")
    var gemId: Int?, // gemId가 null이 아니면 쏘아라 내용받아오기 null이면 등록
    @SerializedName("content1")
    var content1: String?=null,
    @SerializedName("content2")
    var content2: String?=null,
    @SerializedName("content3")
    var content3: String?=null,
    @SerializedName("content4")
    var content4: String?=null,
    @SerializedName("content5")
    var content5: String?=null
) : Parcelable

fun SoaraContent?.setContent(episodeId: Int, contentNum: Int, content: String): SoaraContent {
    return when (contentNum) {
        1 -> SoaraContent(episodeId, null, content1 = content)
        2 -> SoaraContent(episodeId, null, content2 = content)
        3 -> SoaraContent(episodeId, null, content3 = content)
        4 -> SoaraContent(episodeId, null, content4 = content)
        5 -> SoaraContent(episodeId, null, content5 = content)
        else -> SoaraContent(episodeId, null, content1 = content)
    }

}

