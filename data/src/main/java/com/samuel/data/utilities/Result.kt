package com.samuel.data.utilities

sealed class Result<out T : Any?> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val message: String) : Result<Nothing>()
    data class Progress(val progress: Boolean) : Result<Nothing>()

    fun isSuccess(): Boolean = this is Success
    fun isFailure(): Boolean = this is Failure
    fun isProgress(): Boolean = this is Progress

    fun getResultOrNull(): T? {
        return when (this) {
            is Success -> this.data
            else -> null
        }
    }

    fun getErrorOrNull(): String? {
        return when (this) {
            is Failure -> this.message
            else -> null
        }
    }
}
