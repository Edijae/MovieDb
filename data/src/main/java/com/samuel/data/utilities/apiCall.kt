package com.samuel.data.utilities

import retrofit2.Response

suspend fun <T : Any?> apiCall(call: suspend () -> Response<T>): Result<T?> {
    return try {
        call().run {
            if (isSuccessful) {
                return Result.Success(body())
            }
            return Result.Failure(message())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Failure(e.message ?: "")
    }
}