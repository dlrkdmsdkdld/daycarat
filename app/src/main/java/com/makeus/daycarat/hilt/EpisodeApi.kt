package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.AllUserData
import com.makeus.daycarat.data.MonthEpisodeCount
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {



    @GET("episode/date")
    suspend fun getUserMothEpisodeCount(@Query("year") year:Int ): ResponseBody<List<MonthEpisodeCount>>
}