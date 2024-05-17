package com.makeus.daycarat.data.source

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.ActivityTag
import com.makeus.daycarat.data.data.Content
import com.makeus.daycarat.data.data.EpisodeId
import com.makeus.daycarat.data.data.EpisodeKeyword
import com.makeus.daycarat.data.data.GemCount
import com.makeus.daycarat.data.data.GemTotalCount
import com.makeus.daycarat.data.data.SoaraContent
import com.makeus.daycarat.data.paging.GemContentPagingSource
import com.makeus.daycarat.data.paging.GemDetailConetent
import com.makeus.daycarat.domain.source.EpisodeSource
import com.makeus.daycarat.domain.source.GemSource
import com.makeus.daycarat.hilt.EpisodeApi
import com.makeus.daycarat.hilt.GemApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GemRemoteSource @Inject constructor(private val apimodule: GemApi) :
    GemSource {
    override suspend fun getSoaraContent(episodeId: Int): Flow<Resource<SoaraContent>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getSoara(episodeId)
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

    override suspend fun setSoaraContent(data: SoaraContent): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.registerSoara(data)
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

    override suspend fun completeSoara(episodeId: Int): Flow<Resource<Boolean>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.completeSoara(EpisodeId(episodeId))
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
    override suspend fun getGemTotalCount(): Flow<Resource<GemTotalCount>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getTotalGemCount()
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

    override suspend fun getGemTypeCount(): Flow<Resource<GemCount>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getGemCount()
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
    override suspend fun getGemMonthCount(): Flow<Resource<GemTotalCount>>  = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getMonthGemCount()
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

    override suspend fun getMostKeyword(): Flow<Resource<EpisodeKeyword>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getMostKeyword()
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

    override suspend fun getMostActivity(): Flow<Resource<ActivityTag>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getMostActivity()
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
    override suspend fun getContentEpisodeByDatePaging(keyword: String): Flow<PagingData<GemDetailConetent>> {
        return Pager( config = PagingConfig(pageSize = 1) ,
            pagingSourceFactory = { GemContentPagingSource(apimodule,
                keyword = keyword) } ).flow
    }


    override suspend fun getAISoara(episodeId: Int): Flow<Resource<Any>> = flow {
        emit(Resource.loading())
        val response = apimodule.getAISoara(episodeId)
        if (isSuccessful(response.statusCode)) {
            emit(Resource.success(response.result))
        }else if(response.statusCode == 201){ // 키워드생성중
            emit(Resource.working(response.statusCode))
        }else if(response.statusCode == 202){// 키워드 생성실패
            emit(Resource.serverFail("${response.statusCode}"))
        }
        else{
            emit(Resource.error(response.message))
        }
        try {

        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

    override suspend fun getCopyString(episodeId: Int): Flow<Resource<Content>> = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getCopyString(episodeId)
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
