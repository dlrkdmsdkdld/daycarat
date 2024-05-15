package com.makeus.daycarat.domain.repository

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.AllUserData
import com.makeus.daycarat.data.data.UserData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface UserInfoRepository {

    suspend fun updateUserData(
        userData: UserData
    ): Flow<Resource<Boolean>>

    suspend fun getUserData(): Flow<Resource<AllUserData>>

    suspend fun updateProfileImage(
        multipartFile: MultipartBody.Part
    ): Flow<Resource<Boolean>>

    suspend fun deleteUserData(): Flow<Resource<Boolean>>
}