package com.makeus.daycarat.data.repository

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
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.domain.source.EpisodeSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodeRepositoryImpl@Inject constructor(
    private val source: EpisodeSource
) : EpisodeRepository {
    override suspend fun getUserMontlyEpisodeCount(): Flow<Resource<List<MonthEpisodeCount>>> {
        return  source.getUserMontlyEpisodeCount()
    }

    override suspend fun addEpisode(data: EpisodeRegister): Flow<Resource<Boolean>> {
        return  source.addEpisode(data)
    }

    override suspend fun getRecentEpisode(): Flow<Resource<List<EpisodeRecent>>> {
        return  source.getRecentEpisode()
    }

    override suspend fun getActivityTagOder(year: Int?): Flow<Resource<List<EpisodeActivityCounter>>> {
        year?.let {
            return source.getActivityTagOder(year)
        }?: kotlin.run {
            return  source.getActivityTagOder()
        }
    }

    override suspend fun getContentEpisodePaging(
        month: Int,
        year: Int
    ): Flow<PagingData<EpisodeDetailContent>> {
        return  source.getContentEpisodePaging(month , year)
    }

    override suspend fun getContentEpisodePaging(activityTag: String): Flow<PagingData<EpisodeDetailContent>> {
        return  source.getContentEpisodePaging(activityTag)
    }

    override suspend fun getEpisode(episodeId: Int): Flow<Resource<EpisodeFullContent>> {
        return  source.getEpisode(episodeId)
    }

    override suspend fun updatekeyword(data: EpisodeKeywordAndId): Flow<Resource<Boolean>> {
        return  source.updatekeyword(data)
    }

    override suspend fun getTotalEpisodeCount(): Flow<Resource<EpisodeCount>> {
        return  source.getTotalEpisodeCount()
    }

    override suspend fun deleteEpisode(episodeId: Int): Flow<Resource<Boolean>> {
        return  source.deleteEpisode(episodeId)
    }

    override suspend fun updateEpisode(data: EpisodeRegisterWithId): Flow<Resource<Boolean>> {
        return  source.updateEpisode(data)
    }
}

