package com.makeus.daycarat.core.dto

import java.io.Serializable

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> working(data: T?): Resource<T> {
            return Resource(Status.WORKING, data , null)
        }
        fun <T> serverFail(data: T?): Resource<T> {
            return Resource(Status.SERVER_FAIL, data , null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS,
    WORKING,
    SERVER_FAIL,
    ERROR,
    LOADING
}
