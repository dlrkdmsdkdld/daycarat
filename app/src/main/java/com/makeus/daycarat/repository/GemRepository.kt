package com.makeus.daycarat.repository

import android.util.Log
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.EpisodeId
import com.makeus.daycarat.data.SoaraContent
import com.makeus.daycarat.hilt.GemApi
import com.makeus.daycarat.hilt.UserInfoApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil
import com.makeus.daycarat.util.isSuccessful
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



}