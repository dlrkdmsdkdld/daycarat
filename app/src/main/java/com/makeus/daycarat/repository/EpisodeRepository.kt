package com.makeus.daycarat.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.EpisodeKeywordAndId
import com.makeus.daycarat.data.data.EpisodeRegister
import com.makeus.daycarat.data.data.EpisodeRegisterWithId
import com.makeus.daycarat.data.paging.EpisodeContentByDatePagingSource
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.hilt.EpisodeApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil.parseTimeToYear
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val apimodule: EpisodeApi) {

    suspend fun getUserMontlyEpisodeCount() = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getUserMothEpisodeCount(parseTimeToYear())
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

    suspend fun addEpisode(data: EpisodeRegister) = flow {
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

    suspend fun getRecentEpisode() = flow {
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



    suspend fun getActivityTagOderByCount() = flow {
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

    suspend fun getActivityTagOderByDate(year:Int ) = flow {
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

    suspend fun getContentEpisodeByDatePaging(month :Int , year: Int): Flow<PagingData<EpisodeDetailContent>> {
         return Pager( config = PagingConfig(pageSize = 1) ,
             pagingSourceFactory = {EpisodeContentByDatePagingSource(apimodule, year = year , month = month , activityTag = null)} ).flow
    }
    suspend fun getContentEpisodeByCountPaging(activityTag:String): Flow<PagingData<EpisodeDetailContent>> {
        return Pager( config = PagingConfig(pageSize = 1) ,
            pagingSourceFactory = {EpisodeContentByDatePagingSource(apimodule, year = 0 , month = 0 , activityTag = activityTag)} ).flow
    }

    suspend fun getEpisode(episodeId:Int ) = flow {
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
    suspend fun updatekeyword(data: EpisodeKeywordAndId) = flow {
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
    suspend fun getTotalEpisodeCount() = flow {
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

    suspend fun deleteEpisode(episodeId: Int) = flow {
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

    suspend fun updateEpisode(data: EpisodeRegisterWithId) = flow {
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