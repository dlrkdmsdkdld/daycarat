package com.makeus.daycarat.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
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
import com.makeus.daycarat.data.paging.EpisodeContentByDatePagingSource
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.domain.source.EpisodeSource
import com.makeus.daycarat.hilt.EpisodeApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodeRemoteSource @Inject constructor(private val apimodule: EpisodeApi): EpisodeSource {

    override suspend fun getUserMontlyEpisodeCount(): Flow<Resource<List<MonthEpisodeCount>>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getUserMothEpisodeCount(TimeUtil.parseTimeToYear())
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun addEpisode(data: EpisodeRegister): Flow<Resource<Boolean>>  = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.addEpisode(data)
            if (isSuccessful(response.statusCode)){
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun getRecentEpisode(): Flow<Resource<List<EpisodeRecent>>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getRecentThreeEpisode()
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun getActivityTagOder(): Flow<Resource<List<EpisodeActivityCounter>>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getActivityTagCountOderByCount()
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
//                emit(Resource.error(response.statusCode.toString()))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun getActivityTagOder(year: Int): Flow<Resource<List<EpisodeActivityCounter>>>  = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getActivityTagCountOderByDate(year)
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
//                emit(Resource.error(response.statusCode.toString()))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }
    override suspend fun getContentEpisodePaging(
        month: Int,
        year: Int
    ): Flow<PagingData<EpisodeDetailContent>>  {
        return Pager( config = PagingConfig(pageSize = 1) ,
            pagingSourceFactory = { EpisodeContentByDatePagingSource(apimodule, year = year , month = month , activityTag = null) } ).flow
    }

    override suspend fun getContentEpisodePaging(activityTag: String): Flow<PagingData<EpisodeDetailContent>>
    {
        return Pager( config = PagingConfig(pageSize = 1) ,
            pagingSourceFactory = {EpisodeContentByDatePagingSource(apimodule, year = 0 , month = 0 , activityTag = activityTag)} ).flow
    }

    override suspend fun getEpisode(episodeId: Int): Flow<Resource<EpisodeFullContent>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getEpisode(episodeId)
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun updatekeyword(data: EpisodeKeywordAndId): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.updateEpisodeKeyword(data)
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }
    override suspend fun getTotalEpisodeCount(): Flow<Resource<EpisodeCount>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getTotalEpisodeCount()
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun deleteEpisode(episodeId: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.deleteEpisode(episodeId)
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }


    override suspend fun updateEpisode(data: EpisodeRegisterWithId): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.updateEpisode(data)
            if (isSuccessful(response.statusCode)) {
                emit(Resource.success(response.result))
            }else{
                emit(Resource.error(response.message))
            }
        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }
}
