package com.makeus.daycarat.domain.repository

import com.makeus.daycarat.core.dto.Resource
import kotlinx.coroutines.flow.Flow

interface FcmRepository {

    operator fun invoke(
        fcmToken: String
    ): Flow<Resource<Int>>
}