package com.makeus.daycarat.repository

import android.util.Log
import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.AuthToken
import com.makeus.daycarat.hilt.RetrofitInterface
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Constant.ERROR_UNKNOWN
import com.makeus.daycarat.util.Constant.USER_ACCESS_TOKEN
import com.makeus.daycarat.util.Constant.USER_REFRESH_TOKEN
import com.makeus.daycarat.util.SharedPreferenceManager
import com.makeus.daycarat.util.UiEvent
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.flow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apimodule: RetrofitInterface) {

//    suspend fun getCategoryAriticle(accestoken:String):AuthToken?{
//        val result = apimodule.requestKakaoLoginToken(accestoken)
//
//        return result.
//    }
    operator fun invoke(accestoken: String) = flow {
        //            val response = userInfoRepository.postUserInfo(userInfoInput)
        try {
            emit(Resource.loading())
            val response = apimodule.requestKakaoLoginToken(accestoken)
            Log.d(Constant.TAG , " ${response.statusCode} ${response.result?.accessToken}")
            if (isSuccessful(response.statusCode) || response.statusCode == 201) {
                Log.d(Constant.TAG , "response ${response.statusCode} ")
                response.result?.accessToken?.let {
                    SharedPreferenceManager.getInstance().setString(USER_ACCESS_TOKEN , it)
                }
                response.result?.refreshToken?.let {
                    SharedPreferenceManager.getInstance().setString(USER_REFRESH_TOKEN , it)
                }
                emit(Resource.success(response.statusCode))
            }
            else {
                emit(Resource.error(response.statusCode.toString()))
            }

        } catch (e: Exception) {
//            if (e is CancellationException) throw e
            e.printStackTrace()
            emit(Resource.error(e.localizedMessage ?: ERROR_UNKNOWN))
        }
    }


}
