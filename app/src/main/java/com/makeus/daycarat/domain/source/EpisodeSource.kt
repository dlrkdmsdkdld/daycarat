package com.makeus.daycarat.domain.source

import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.EpisodeActivityCounter
import com.makeus.daycarat.data.data.EpisodeCount
import com.makeus.daycarat.data.data.EpisodeFullContent
import com.makeus.daycarat.data.data.EpisodeKeywordAndId
import com.makeus.daycarat.data.data.EpisodeRecent
import com.makeus.daycarat.data.data.EpisodeRegister
import com.makeus.daycarat.data.data.EpisodeRegisterWithId
import com.makeus.daycarat.data.data.MonthEpisodeCount
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import kotlinx.coroutines.flow.Flow

interface EpisodeSource {

    suspend fun getUserMontlyEpisodeCount(): Flow<Resource<List<MonthEpisodeCount>>>

    suspend fun addEpisode(data: EpisodeRegister): Flow<Resource<Boolean>>

    suspend fun getRecentEpisode(): Flow<Resource<List<EpisodeRecent>>>

    suspend fun getActivityTagOder(): Flow<Resource<List<EpisodeActivityCounter>>>

    suspend fun getActivityTagOder(year: Int): Flow<Resource<List<EpisodeActivityCounter>>>

    suspend fun getContentEpisodePaging(
        month: Int,
        year: Int
    ): Flow<PagingData<EpisodeDetailContent>>

    suspend fun getContentEpisodePaging(activityTag: String): Flow<PagingData<EpisodeDetailContent>>

    suspend fun getEpisode(episodeId: Int): Flow<Resource<EpisodeFullContent>>

    suspend fun updatekeyword(data: EpisodeKeywordAndId): Flow<Resource<Boolean>>

    suspend fun getTotalEpisodeCount(): Flow<Resource<EpisodeCount>>

    suspend fun deleteEpisode(episodeId: Int): Flow<Resource<Boolean>>

    suspend fun updateEpisode(
        data: EpisodeRegisterWithId
    ): Flow<Resource<Boolean>>
}