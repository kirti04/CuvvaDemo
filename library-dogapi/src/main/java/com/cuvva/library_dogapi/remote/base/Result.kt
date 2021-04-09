package com.cuvva.library_dogapi.remote.base

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val message: String? = null) : Result<Nothing>()
}