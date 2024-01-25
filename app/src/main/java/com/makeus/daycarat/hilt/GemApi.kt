package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.EpisodeFullContent
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.data.SoaraContent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GemApi {

    @GET("gem/soara")
    suspend fun registerSoara(@Body data: SoaraContent): ResponseBody<Boolean>



}