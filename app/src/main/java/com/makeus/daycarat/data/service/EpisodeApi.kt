package com.makeus.daycarat.data.service

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.data.data.EpisodeKeywordAndId
import com.makeus.daycarat.data.data.EpisodeRecent
import com.makeus.daycarat.data.data.EpisodeRegister
import com.makeus.daycarat.data.data.MonthEpisodeCount
import com.makeus.daycarat.data.data.EpisodeCount
import com.makeus.daycarat.data.data.EpisodeRegisterWithId
import com.makeus.daycarat.data.data.EpisodeDetailContent
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @POST("episode/register")
    suspend fun addEpisode(@Body data: EpisodeRegister): ResponseBody<Boolean>

    @GET("episode/recent")
    suspend fun getRecentThreeEpisode(): ResponseBody<List<EpisodeRecent>>

    @GET("episode/date")
    suspend fun getUserMothEpisodeCount(@Query("year") year:Int ): ResponseBody<List<MonthEpisodeCount>>

    @GET("episode/activity")
    suspend fun getActivityTagCountOderByCount(): ResponseBody<List<EpisodeActivityCounter>>

    @GET("episode/date")
    suspend fun getActivityTagCountOderByDate(@Query("year") year:Int): ResponseBody<List<EpisodeActivityCounter>>


    @GET("episode/date/{year}/{month}")
    suspend fun getEpisodeOderByDate(@Path("year") year:Int , @Path("month") month:Int ,@Query("cursorId") cursorId: Int? = null, @Query("pageSize") pageSize: Int = 6 ): ResponseBody<List<EpisodeDetailContent>>

    @GET("episode/activity/{activityTagName}")
    suspend fun getEpisodeOderByCount(@Path("activityTagName") activityTagName:String, @Query("cursorId") cursorId: Int? = null, @Query("pageSize") pageSize: Int = 6): ResponseBody<List<EpisodeDetailContent>>

    @GET("episode/{episodeId}")
    suspend fun getEpisode(@Path("episodeId") episodeId:Int): ResponseBody<EpisodeFullContent>

    @PATCH("episode/keyword")
    suspend fun updateEpisodeKeyword(@Body data: EpisodeKeywordAndId): ResponseBody<Boolean>

    @GET("episode/count/all")
    suspend fun getTotalEpisodeCount(): ResponseBody<EpisodeCount>

    @DELETE("episode/delete/{episodeId}")
    suspend fun deleteEpisode(@Path("episodeId") episodeId: Int): ResponseBody<Boolean>

    @PATCH("episode/update")
    suspend fun updateEpisode(@Body data: EpisodeRegisterWithId): ResponseBody<Boolean>

}

