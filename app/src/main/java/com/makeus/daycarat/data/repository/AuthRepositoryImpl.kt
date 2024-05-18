package com.makeus.daycarat.data.repository

import android.util.Log
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.domain.repository.AuthRepository
import com.makeus.daycarat.data.service.LoginApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl@Inject constructor(private val apimodule: LoginApi)
    : AuthRepository
{
    override fun invoke(accestoken: String): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.loading())
            val response = apimodule.requestKakaoLoginToken(accestoken)
            Log.d(Constant.TAG , " ${response.statusCode} ${response.result?.accessToken}")
            if (isSuccessful(response.statusCode) || response.statusCode == 201) {
                Log.d(Constant.TAG , "response ${response.statusCode} ")
                response.result?.accessToken?.let {
                    SharedPreferenceManager.getInstance().setString(Constant.USER_ACCESS_TOKEN, it)
                }
                response.result?.refreshToken?.let {
                    SharedPreferenceManager.getInstance().setString(Constant.USER_REFRESH_TOKEN, it)
                }
                emit(Resource.success(response.statusCode))
            }
            else {
                emit(Resource.error(response.statusCode.toString()))
            }

        } catch (e: Exception) {
//            if (e is CancellationException) throw e
            e.printStackTrace()
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
        }
    }
}