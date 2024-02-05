package com.makeus.daycarat.hilt

import com.google.gson.JsonElement
import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.AllUserData
import com.makeus.daycarat.data.AuthToken
import com.makeus.daycarat.data.UserData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface UserInfoApi {
    @PATCH("user/userInfo")
    suspend fun updateUserInfo(@Body data:UserData): ResponseBody<Boolean>

    @GET("user/userInfo")
    suspend fun getUserInfo(): ResponseBody<AllUserData>

    @Multipart
    @POST("user/profile")
    suspend fun updateUserImage(@Part multipartFile: MultipartBody.Part): ResponseBody<Boolean>
}

//@Multipart
//@PUT("/user/profile")
//fun editProfile(@Part file:MultipartBody.Part?, @Part("isImgEdited") isImgEdited: RequestBody, @Part("nickname") nickname: RequestBody,
//                @Part("beMyMessage") beMyMessage: RequestBody
//) : Call<JsonElement>
