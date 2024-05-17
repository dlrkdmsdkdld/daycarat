package com.makeus.daycarat.data.repository

import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.ActivityTag
import com.makeus.daycarat.data.data.Content
import com.makeus.daycarat.data.data.EpisodeKeyword
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.data.GemTotalCount
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.data.paging.GemDetailConetent
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.domain.repository.GemRepository
import com.makeus.daycarat.domain.source.EpisodeSource
import com.makeus.daycarat.domain.source.GemSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GemRepositoryImpl@Inject constructor(
    private val source: GemSource
) : GemRepository {
    override suspend fun getSoaraContent(episodeId: Int): Flow<Resource<SoaraContent>> {
        return source.getSoaraContent(episodeId)
    }

    override suspend fun setSoaraContent(data: SoaraContent): Flow<Resource<Boolean>> {
        return source.setSoaraContent(data)
    }

    override suspend fun completeSoara(episodeId: Int): Flow<Resource<Boolean>> {
        return source.completeSoara(episodeId)
    }

    override suspend fun getGemTotalCount(): Flow<Resource<GemTotalCount>> {
        return source.getGemTotalCount()
    }

    override suspend fun getGemTypeCount(): Flow<Resource<GemCount>> {
        return source.getGemTypeCount()
    }

    override suspend fun getGemMonthCount(): Flow<Resource<GemTotalCount>> {
        return source.getGemMonthCount()
    }

    override suspend fun getMostKeyword(): Flow<Resource<EpisodeKeyword>> {
        return source.getMostKeyword()
    }

    override suspend fun getMostActivity(): Flow<Resource<ActivityTag>> {
        return source.getMostActivity()
    }

    override suspend fun getContentEpisodeByDatePaging(keyword: String): Flow<PagingData<GemDetailConetent>> {
        return source.getContentEpisodeByDatePaging(keyword)
    }

    override suspend fun getAISoara(episodeId: Int): Flow<Resource<Any>> {
        return source.getAISoara(episodeId)
    }

    override suspend fun getCopyString(episodeId: Int): Flow<Resource<Content>> {
        return source.getCopyString(episodeId)
    }

}