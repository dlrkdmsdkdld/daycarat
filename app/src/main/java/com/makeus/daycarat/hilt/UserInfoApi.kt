package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.AllUserData
import com.makeus.daycarat.data.AuthToken
import com.makeus.daycarat.data.UserData
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Query

interface UserInfoApi {
    @PATCH("user/userInfo")
    suspend fun updateUserInfo(@Body data:UserData): ResponseBody<Boolean>

    @PATCH("user/userInfo")
    suspend fun getUserInfo(@Body data:UserData): ResponseBody<AllUserData>
}