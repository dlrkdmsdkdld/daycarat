package com.makeus.daycarat.domain.repository

import com.makeus.daycarat.core.dto.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    operator fun invoke(accestoken: String) : Flow<Resource<Int>>
}