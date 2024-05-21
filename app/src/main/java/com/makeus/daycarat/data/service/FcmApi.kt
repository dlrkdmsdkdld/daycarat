package com.makeus.daycarat.data.service

import com.makeus.daycarat.presentation.util.ResponseBody
import com.makeus.daycarat.data.data.UserData
import com.makeus.daycarat.data.data.announcement
import retrofit2.http.Body
import retrofit2.http.PATCH

interface FcmApi {

    @PATCH("user/userInfo")
    suspend fun updataFcmToken(@Body data: UserData): ResponseBody<Boolean> //fcm token 업데이트 용 메서드

    @PATCH("announcement")
    suspend fun updataAnnouncement(@Body data: announcement): ResponseBody<Boolean> //fcm token 업데이트 용 메서드
}

