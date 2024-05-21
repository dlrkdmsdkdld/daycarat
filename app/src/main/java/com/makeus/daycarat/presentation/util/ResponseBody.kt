package com.makeus.daycarat.presentation.util

import java.io.Serializable

data class ResponseBody<T: Any?>(
    val statusCode: Int, // http 상태 코드
//    val code: String, // 서버 측에서 자체적으로 정한 코드
    val message: String,
    val result: T?
): Serializable