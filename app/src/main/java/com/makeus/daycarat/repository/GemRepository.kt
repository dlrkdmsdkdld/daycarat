package com.makeus.daycarat.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.EpisodeId
import com.makeus.daycarat.data.SoaraContent
import com.makeus.daycarat.data.paging.EpisodeContentByDatePagingSource
import com.makeus.daycarat.data.paging.EpisodeDetailContent
import com.makeus.daycarat.data.paging.GemContentPagingSource
import com.makeus.daycarat.data.paging.GemDetailConetent
import com.makeus.daycarat.hilt.GemApi
import com.makeus.daycarat.hilt.UserInfoApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GemRepository @Inject constructor(private val apimodule: GemApi) {

    suspend fun getSoaraContent(episodeId:Int) = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.getSoara(episodeId)
            Log.d("GHLEE","statu ${response.statusCode}")
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
    suspend fun setSoaraContent(data: SoaraContent) = flow {
        emit(Resource.loading())
        try {
            val response = apimodule.registerSoara(data)
            Log.d("GHLEE","statu ${response.statusCode}")
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
    suspend fun completeSoara(episodeId: Int) = flow {
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

    suspend fun getGemTotalCount() = flow {
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

    suspend fun getGemTpyeCount() = flow {
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

    suspend fun getGemMonthCount() = flow {
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

    suspend fun getMostKeyword() = flow {
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

    suspend fun getMostActivity() = flow {
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

    suspend fun getContentEpisodeByDatePaging(keyword: String): Flow<PagingData<GemDetailConetent>> {
        return Pager( config = PagingConfig(pageSize = 1) ,
            pagingSourceFactory = { GemContentPagingSource(apimodule,
                keyword = keyword) } ).flow
    }

    suspend fun getAISoara(episodeId: Int) = flow {
        emit(Resource.loading())
        val response = apimodule.getAISoara(episodeId)
        if (isSuccessful(response.statusCode)) {
            emit(Resource.success(response.result))
        }else if(response.statusCode == 404){ // 키워드생성중
            emit(Resource.error("${response.statusCode}"))
        }else if(response.statusCode == 500){// 키워드 생성실패
            emit(Resource.error("${response.statusCode}"))
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

    suspend fun getCopyString(episodeId: Int) = flow {
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