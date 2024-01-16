package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.AllUserData
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.data.MonthEpisodeCount
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @POST("episode/register")
    suspend fun addEpisode(@Body data: EpisodeRegister): ResponseBody<Boolean>

    @GET("episode/date")
    suspend fun getUserMothEpisodeCount(@Query("year") year:Int ): ResponseBody<List<MonthEpisodeCount>>
}