package com.makeus.daycarat.data.service

import com.makeus.daycarat.presentation.util.ResponseBody
import com.makeus.daycarat.data.data.ActivityTag
import com.makeus.daycarat.data.data.Content
import com.makeus.daycarat.data.data.EpisodeId
import com.makeus.daycarat.data.data.EpisodeKeyword
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.data.GemSoaraAIContent
import com.makeus.daycarat.data.data.GemTotalCount
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.data.data.GemDetailConetent
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface GemApi {

    @PATCH("gem/soara")
    suspend fun registerSoara(@Body data: SoaraContent): ResponseBody<Boolean>


    @GET("gem/soara/{episodeId}")
    suspend fun getSoara(@Path("episodeId") episodeId: Int): ResponseBody<SoaraContent>

    @POST("gem/register")
    suspend fun completeSoara(@Body episodeId: EpisodeId): ResponseBody<Boolean>


    @GET("gem/count")
    suspend fun getTotalGemCount( ): ResponseBody<GemTotalCount>

    @GET("gem/keyword")
    suspend fun getGemCount( ): ResponseBody<GemCount>

    @GET("gem/report/month-count")
    suspend fun getMonthGemCount( ): ResponseBody<GemTotalCount>
    @GET("gem/report/keyword")
    suspend fun getMostKeyword( ): ResponseBody<EpisodeKeyword>


    @GET("gem/report/activity")
    suspend fun getMostActivity( ): ResponseBody<ActivityTag>


    @GET("gem/keyword/{keyword}")
    suspend fun getGemKeywordList(@Path("keyword") keyword:String, @Query("cursorId") cursorId: Int? = null, @Query("pageSize") pageSize: Int = 6 ): ResponseBody<List<GemDetailConetent>>

    @GET("gem/recommend/{episodeId}")
    suspend fun getAISoara(@Path("episodeId") episodeId: Int): ResponseBody<GemSoaraAIContent>

    @GET("gem/clipboard/{episodeId}")
    suspend fun getCopyString(@Path("episodeId") episodeId: Int): ResponseBody<Content>



}