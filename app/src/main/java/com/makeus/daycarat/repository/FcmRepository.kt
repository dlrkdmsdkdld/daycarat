package com.makeus.daycarat.repository

import android.util.Log
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.UserData
import com.makeus.daycarat.hilt.FcmApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FcmRepository @Inject constructor(private val apimodule: FcmApi) {

    operator fun invoke(fcmToken: String) = flow {
        try {
            emit(Resource.loading())
            val response = apimodule.updataFcmToken(UserData(fcmToken = fcmToken))
            if (isSuccessful(response.statusCode) || response.statusCode == 201) {
                Log.d(Constant.TAG , "response ${response.statusCode} ")
                emit(Resource.success(response.statusCode))
            }
            else {
                emit(Resource.error(response.statusCode.toString()))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
        }
    }

}