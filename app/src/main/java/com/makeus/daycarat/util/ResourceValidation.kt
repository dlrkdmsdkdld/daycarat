package com.makeus.daycarat.util

import com.makeus.daycarat.core.dto.Resource
import com.makeus.daycarat.util.Constant.ERROR_UNKNOWN
import retrofit2.Call

fun <T> isLoading(resource: Resource<T>) = resource == Resource.loading(null)

fun <T> isSuccess(resource: Resource<T>) = resource == Resource.success(resource.data)

fun <T> isError(resource: Resource<T>) = resource == Resource.error(resource.message ?: ERROR_UNKNOWN, null)

