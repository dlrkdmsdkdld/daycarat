package com.makeus.daycarat.domain.repository

import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.ActivityTag
import com.makeus.daycarat.data.data.Content
import com.makeus.daycarat.data.data.EpisodeKeyword
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.data.GemTotalCount
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.data.data.GemDetailConetent
import kotlinx.coroutines.flow.Flow

interface GemRepository {

    suspend fun getSoaraContent(episodeId: Int): Flow<Resource<SoaraContent>>

    suspend fun setSoaraContent(
        data: SoaraContent
    ): Flow<Resource<Boolean>>


    suspend fun completeSoara(
        episodeId: Int
    ): Flow<Resource<Boolean>>

    suspend fun getGemTotalCount(): Flow<Resource<GemTotalCount>>

    suspend fun getGemTypeCount(): Flow<Resource<GemCount>>
    suspend fun getGemMonthCount(): Flow<Resource<GemTotalCount>>
    suspend fun getMostKeyword(): Flow<Resource<EpisodeKeyword>>
    suspend fun getMostActivity(): Flow<Resource<ActivityTag>>
    suspend fun getContentEpisodeByDatePaging(
        keyword: String
    ): Flow<PagingData<GemDetailConetent>>

    suspend fun getAISoara(
        episodeId: Int
    ): Flow<Resource<Any>>

    suspend fun getCopyString(
        episodeId: Int
    ): Flow<Resource<Content>>
}