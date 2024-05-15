package com.makeus.daycarat.data.repository

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.data.data.AllUserData
import com.makeus.daycarat.data.data.UserData
import com.makeus.daycarat.domain.repository.EpisodeRepository
import com.makeus.daycarat.domain.repository.UserInfoRepository
import com.makeus.daycarat.domain.source.EpisodeSource
import com.makeus.daycarat.domain.source.UserSource
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val source: UserSource
) : UserInfoRepository {
    override suspend fun updateUserData(userData: UserData): Flow<Resource<Boolean>> {
        return source.updateUserData(userData)
    }

    override suspend fun getUserData(): Flow<Resource<AllUserData>> {
        return source.getUserData()
    }

    override suspend fun updateProfileImage(multipartFile: MultipartBody.Part): Flow<Resource<Boolean>> {
        return source.updateProfileImage(multipartFile)
    }

    override suspend fun deleteUserData(): Flow<Resource<Boolean>> {
        return source.deleteUserData()
    }
}