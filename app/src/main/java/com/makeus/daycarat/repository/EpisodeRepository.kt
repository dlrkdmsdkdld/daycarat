package com.makeus.daycarat.repository

import android.util.Log
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.EpisodeRegister
import com.makeus.daycarat.hilt.EpisodeApi
import com.makeus.daycarat.hilt.RetrofitInterface
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.TimeUtil.parseTimeToYear
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class EpisodeRepository @Inject constructor(private val apimodule: EpisodeApi) {

    suspend fun getUserMontlyEpisodeCount() = flow {
        Log.d(Constant.TAG , "getUserMontlyEpisodeCount parseTimeToYear ${parseTimeToYear()}")
        emit(Resource.loading())
        try {
            val response = apimodule.getUserMothEpisodeCount(parseTimeToYear())
            if (isSuccessful(response.statusCode)) {
                response.result?.forEach {
                    Log.d(Constant.TAG , "it ${it.month} quantity ${it.quantity}")
                }
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

    suspend fun addEpisode(data: EpisodeRegister) = flow {
        emit(Resource.loading())
        val response = apimodule.addEpisode(data)
        if (isSuccessful(response.statusCode)){
            emit(Resource.success(response.result))
        }else{
            emit(Resource.error(response.message))
        }


    }



}