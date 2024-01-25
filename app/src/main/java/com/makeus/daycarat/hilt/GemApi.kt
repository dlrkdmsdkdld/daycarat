package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.data.SoaraContent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface GemApi {

    @PATCH("gem/soara")
    suspend fun registerSoara(@Body data: SoaraContent): ResponseBody<Boolean>

    @POST("gem/register")
    suspend fun completeSoara(@Body episodeId: Int): ResponseBody<Boolean>
    @GET("gem/soara/{episodeId}")
    suspend fun getSoara(@Path("episodeId") episodeId: Int): ResponseBody<SoaraContent>



}