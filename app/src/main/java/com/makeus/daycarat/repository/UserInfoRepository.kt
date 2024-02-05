package com.makeus.daycarat.repository

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.hilt.RetrofitInterface
import com.makeus.daycarat.hilt.UserInfoApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UserInfoRepository  @Inject constructor(private val userInfoApi: UserInfoApi) {


    suspend fun updateUserData(userData: UserData) = flow{
        emit(Resource.loading())
        try {
            val response = userInfoApi.updateUserInfo(userData)
            if (isSuccessful(response.statusCode)) {
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

    suspend fun getUserData() = flow{
        emit(Resource.loading())
        try {
            val response = userInfoApi.getUserInfo()
            if (isSuccessful(response.statusCode)) {
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

    suspend fun updateProfileImage(multipartFile: MultipartBody.Part) = flow{
        emit(Resource.loading())
        val response = userInfoApi.updateUserImage(multipartFile)
        if (isSuccessful(response.statusCode)) {
            emit(Resource.success(response.result))
        }else{
            emit(Resource.error(response.message))
//                emit(Resource.error(response.statusCode.toString()))
        }
        try {

        }catch (e:Exception){
            emit(Resource.error(e.localizedMessage ?: Constant.ERROR_UNKNOWN))
            e.printStackTrace()
        }
    }

}