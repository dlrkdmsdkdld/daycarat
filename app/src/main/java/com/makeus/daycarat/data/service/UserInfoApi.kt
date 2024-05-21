package com.makeus.daycarat.data.service

import com.makeus.daycarat.presentation.util.ResponseBody
import com.makeus.daycarat.data.data.AllUserData
import com.makeus.daycarat.data.data.UserData
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface UserInfoApi {
    @PATCH("user/userInfo")
    suspend fun updateUserInfo(@Body data: UserData): ResponseBody<Boolean>

    @GET("user/userInfo")
    suspend fun getUserInfo(): ResponseBody<AllUserData>

    @Multipart
    @POST("user/profile")
    suspend fun updateUserImage(@Part multipartFile: MultipartBody.Part): ResponseBody<Boolean>


    @DELETE("user/delete")
    suspend fun resignUser(): ResponseBody<Boolean>
}

//@Multipart
//@PUT("/user/profile")
//fun editProfile(@Part file:MultipartBody.Part?, @Part("isImgEdited") isImgEdited: RequestBody, @Part("nickname") nickname: RequestBody,
//                @Part("beMyMessage") beMyMessage: RequestBody
//) : Call<JsonElement>
