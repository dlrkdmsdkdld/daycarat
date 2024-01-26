package com.makeus.daycarat.hilt

import com.google.gson.JsonElement
import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.data.EpisodeId
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.data.SoaraContent
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GemApi {

    @PATCH("gem/soara")
    suspend fun registerSoara(@Body data: SoaraContent): ResponseBody<Boolean>


    @GET("gem/soara/{episodeId}")
    suspend fun getSoara(@Path("episodeId") episodeId: Int): ResponseBody<SoaraContent>

    @POST("gem/register")
    suspend fun completeSoara(@Body episodeId: EpisodeId): ResponseBody<Boolean>


}