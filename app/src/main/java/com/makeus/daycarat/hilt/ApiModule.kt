package com.makeus.daycarat.hilt

import android.util.Log
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.Extensions.isJsonArray
import com.makeus.daycarat.util.Extensions.isJsonObject
import com.makeus.daycarat.util.SharedPreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

//    @Singleton
//    @Provides
//    fun provideAPIService(auth:RetrofitInterface): RetrofitInterface{
//        return RetrofitInterface.create()
//    }

    @Provides
    @Singleton // have a singleton...
    fun provideHttpClient(): OkHttpClient {
            val client = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Log.d(Constant.TAG, "RetrofitClient - log() called / message: $message")
                when {
                    message.isJsonObject() -> Log.d(Constant.TAG, JSONObject(message).toString(4))

                    message.isJsonArray() -> Log.d(Constant.TAG, JSONArray(message).toString(4))
                    else -> {
                        try {
                            Log.d(Constant.TAG, JSONObject(message).toString(4))
                        } catch (e: Exception) {
                            Log.d(Constant.TAG, message)
                        }
                    }

                }
            }
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            //위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가한다.
            client.addInterceptor(loggingInterceptor)
            //기본 파라미터 인터셉터 설정
            val baseParameterInterceptor : Interceptor = (object  : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
//                    var token = SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN,"")
                    Log.d(Constant.TAG,"RetrofitClient - Interceptor called ")
                    var originalRequest = chain.request().newBuilder()
                        .build()

                    val finalRequest = originalRequest.newBuilder()
                        .method(originalRequest.method,originalRequest.body)

//                    if (token.isNotEmpty()){
//
//                        finalRequest.addHeader("Authorization","Bearer $token")
//                    }

                    val response = chain.proceed(finalRequest.build())


                    return response
                }

            })
            client.addInterceptor(baseParameterInterceptor)
//        val logger = HttpLoggingInterceptor().apply {
//            level =
//                HttpLoggingInterceptor.Level.BODY
//        }
//        var token = SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN,"")
//        val interceptor = Interceptor { chain -> //데이터가 서버로 부터 왔다갔다하는걸 관장하는 변수
//            with(chain) {
//                val newRequest = request().newBuilder()
//                if (token.isNotEmpty()){
//                    newRequest.addHeader("Authorization","Bearer $token")
//                }
//                proceed(newRequest.build())
//            }
//        }
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logger)
//            .addInterceptor(interceptor) //만든 인터셉터를 okhttp 변수에 넣어줌
//            .build()

        return client.build()
    }
    @Provides
    @Singleton
    fun provideTokenInterceptor(): Interceptor = Interceptor { chain -> //데이터가 서버로 부터 왔다갔다하는걸 관장하는 변수
        with(chain) {
            val newRequest = request().newBuilder()
            newRequest.addHeader("Authorization","Bearer ${SharedPreferenceManager.getInstance().getString(Constant.USER_ACCESS_TOKEN,"")}")
            proceed(newRequest.build())
        }
    }

    @Provides
    @Singleton
    fun provideService(okHttpClient: OkHttpClient): RetrofitInterface =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitInterface::class.java)
    @Provides
    @Singleton
    fun provideUserInfoApi(okHttpClient: OkHttpClient , tokenIntercptor:Interceptor): UserInfoApi =
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient.newBuilder().addInterceptor(tokenIntercptor).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserInfoApi::class.java)



}