package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.UserData
import retrofit2.http.Body
import retrofit2.http.PATCH

interface FcmApi {

    @PATCH("user/userInfo")
    suspend fun updataFcmToken(@Body data: UserData): ResponseBody<Boolean> //fcm token 업데이트 용 메서드

    @PATCH("announcement")
    suspend fun updataAnnouncement(@Body data: announcement): ResponseBody<Boolean> //fcm token 업데이트 용 메서드
}

data class announcement(val title:String , val content:String)