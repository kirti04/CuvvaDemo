package com.cuvva.library_dogapi.remote.base

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppResponse<T>(
    val message: T
)