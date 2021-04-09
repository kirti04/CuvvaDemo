package com.cuvva.library_dogapi.remote.base

import org.json.JSONObject
import retrofit2.Response

internal abstract class BaseRemoteSource {

    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> {
        return try {
            handleSuccess(apiCall.invoke())
        } catch (e: Exception) {
            Result.Error(e.message ?: "")
        }
    }

    private fun <T> handleSuccess(response: Response<T>): Result<T> {
        val responseBodyValue = response.body()
        return when {
            response.code() in 200..299 && responseBodyValue != null -> Result.Success(responseBodyValue)
            else -> {
                val errorBodyJson = try {
                    JSONObject(response?.errorBody()?.string())
                } catch (e: Exception) {
                    null
                }

                val message = errorBodyJson?.optString("message", null)
                Result.Error(message)
            }
        }
    }
}