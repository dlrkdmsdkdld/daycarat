package com.makeus.daycarat.hilt

import android.util.Log
import com.makeus.daycarat.BuildConfig
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
import javax.inject.Qualifier

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class NoAuth // 로그인할때 , 토큰필요없는 부분

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Auth //로그인시 ,토큰 필요한부분

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
                    Log.d(Constant.TAG,"RetrofitClient - Interceptor called ")
                    var originalRequest = chain.request().newBuilder()
                        .build()

                    val finalRequest = originalRequest.newBuilder()
                        .method(originalRequest.method,originalRequest.body)
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
    @NoAuth   //This will differentiate retrofit object
    fun retrofitNoAuth(
        client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()


    @Provides
    @Singleton
    @Auth   //This will differentiate retrofit object
    fun retrofitAuth(client: OkHttpClient, tokenIntercptor:Interceptor, ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.newBuilder().addInterceptor(tokenIntercptor).build())
            .baseUrl(BuildConfig.BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideService(@NoAuth retrofit: Retrofit): RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

    @Provides
    @Singleton
    fun provideUserInfoApi(@Auth retrofit: Retrofit): UserInfoApi = retrofit.create(UserInfoApi::class.java)

    @Provides
    @Singleton
    fun provideEpisodeService(@Auth retrofit: Retrofit): EpisodeApi = retrofit.create(EpisodeApi::class.java)
    @Provides
    @Singleton
    fun provideGemService(@Auth retrofit: Retrofit): GemApi = retrofit.create(GemApi::class.java)



}