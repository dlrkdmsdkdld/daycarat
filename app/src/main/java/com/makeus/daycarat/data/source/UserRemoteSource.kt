package com.makeus.daycarat.data.source

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.AllUserData
import com.makeus.daycarat.data.data.UserData
import com.makeus.daycarat.domain.source.UserSource
import com.makeus.daycarat.data.service.UserInfoApi
import com.makeus.daycarat.util.Constant
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UserRemoteSource @Inject constructor(private val apimodule: UserInfoApi): UserSource {
    override suspend fun updateUserData(userData: UserData): Flow<Resource<Boolean>> = flow{
        emit(Resource.loading())
        try {
            val response = apimodule.updateUserInfo(userData)
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

    override suspend fun getUserData(): Flow<Resource<AllUserData>> = flow{
        emit(Resource.loading())
        try {
            val response = apimodule.getUserInfo()
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

    override suspend fun updateProfileImage(multipartFile: MultipartBody.Part) = flow{
        emit(Resource.loading())
        val response = apimodule.updateUserImage(multipartFile)
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

    override suspend fun deleteUserData(): Flow<Resource<Boolean>> = flow{
        emit(Resource.loading())
        try {
            val response = apimodule.resignUser()
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
}
