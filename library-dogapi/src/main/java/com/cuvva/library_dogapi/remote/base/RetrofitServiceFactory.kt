package com.cuvva.library_dogapi.remote.base

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

internal class RetrofitServiceFactory @Inject constructor() {

    fun <T> make(service: Class<T>): T = makeRetrofit().create(service)

    private fun makeRetrofit() = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .client(makeHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private fun makeHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .build()
    }
}