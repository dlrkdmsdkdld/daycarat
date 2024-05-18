package com.makeus.daycarat.hilt

import com.makeus.daycarat.core.dto.ResponseBody
import com.makeus.daycarat.data.data.AuthToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    //kr
//    @GET("top-headlines?country=us")
//    suspend fun requestTopHeadline(@Query("page") page: Int = 1,
//                                   @Query("pageSize") pageSize: Int = 10): NewsResponse
    @GET("user/oauth/kakao")
    suspend fun requestKakaoLoginToken(@Query("accessToken") accessToken:String): ResponseBody<AuthToken>

    companion object {
        private const val BASE_URL =
            "https://www.daycarat.shop/api/"

        fun create(): RetrofitInterface {
            val logger = HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY
            }
            val interceptor = Interceptor { chain -> //데이터가 서버로 부터 왔다갔다하는걸 관장하는 변수
                with(chain) {
                    val newRequest = request().newBuilder()
                        .build()
                    proceed(newRequest)
                }
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(interceptor) //만든 인터셉터를 okhttp 변수에 넣어줌
                .build()

            return Retrofit.Builder() /// 위에서만든 변수들을 이용해서 레트로핏 변수 생성 ->retrofit을 이용해서 서버로부터 기사 데이터를 받아오기때문에 필요함
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) //서버로 부터 받아온 json 값들을 커스텀 클래스로 변환시키기 위해 설정하는 함수
                .build()
                .create(RetrofitInterface::class.java)
        }



    }
}