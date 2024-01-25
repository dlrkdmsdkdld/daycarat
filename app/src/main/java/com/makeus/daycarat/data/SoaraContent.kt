package com.makeus.daycarat.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SoaraContent(var episodeId:Int?  , var gemId:Int?, // gemId가 null이 아니면 쏘아라 내용받아오기 null이면 등록
                           var content1:String ="" ,var content2:String ="" ,
                        var content3:String ="" ,var content4:String ="" ,
                        var content5:String ="" ): Parcelable

fun SoaraContent?.setContent(episodeId: Int , contentNum:Int, content:String): SoaraContent {
    return when(contentNum){
        1 -> SoaraContent(episodeId ,null , content1 =content )
        2 -> SoaraContent(episodeId ,null , content2 =content )
        3 -> SoaraContent(episodeId ,null , content3 =content )
        4 -> SoaraContent(episodeId ,null , content4 =content )
        5 -> SoaraContent(episodeId ,null , content5 =content )
        else -> SoaraContent(episodeId ,null , content1 =content )
    }

}

