package com.makeus.daycarat.domain.repository

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.data.MonthEpisodeCount
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    suspend fun getUserMontlyEpisodeCount() : Flow<Resource<ResponseBody<List<MonthEpisodeCount>>>>

//
//    fun requestArticleData(
//        type: String,
//        category: Int
//    ): Flow<PagingData<ResponseArticleData.ResultArticleData>>
}