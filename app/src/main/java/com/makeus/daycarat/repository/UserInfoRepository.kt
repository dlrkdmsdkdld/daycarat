package com.makeus.daycarat.repository

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.UserData
import com.makeus.daycarat.hilt.RetrofitInterface
import com.makeus.daycarat.hilt.UserInfoApi
import com.makeus.daycarat.util.isSuccessful
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserInfoRepository  @Inject constructor(private val userInfoApi: UserInfoApi) {


    suspend fun updateUserData(userData: UserData) = flow{
        emit(Resource.loading())
        val response = userInfoApi.updateUserInfo(userData)
        if (isSuccessful(response.statusCode)) {
            emit(Resource.success(response.result))
        }else{
            emit(Resource.error(response.statusCode.toString()))
        }


    }
}