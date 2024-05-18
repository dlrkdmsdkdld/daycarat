package com.makeus.daycarat.data.service

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.data.AuthToken
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginApi {
    //kr
//    @GET("top-headlines?country=us")
//    suspend fun requestTopHeadline(@Query("page") page: Int = 1,
//                                   @Query("pageSize") pageSize: Int = 10): NewsResponse
    @GET("user/oauth/kakao")
    suspend fun requestKakaoLoginToken(@Query("accessToken") accessToken:String): ResponseBody<AuthToken>

}