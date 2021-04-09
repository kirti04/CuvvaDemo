package com.cuvva.library_dogapi.remote.source

import com.cuvva.library_dogapi.remote.base.AppResponse
import retrofit2.Response
import retrofit2.http.*

internal interface BreedsService {
    @GET("breeds/list")
    suspend fun getBreeds(): Response<AppResponse<Array<String>>>

    @GET("breed/{breedName}/list")
    suspend fun getSubBreeds(
        @Path("breedName") name: String
    ): Response<AppResponse<Array<String>>>

    @GET("breed/{breedName}/images/random")
    suspend fun getBreedImage(
        @Path("breedName") name: String
    ): Response<AppResponse<String>>

    @GET("breed/{breedName}/{subBreedName}/images/random")
    suspend fun getSubBreedImage(
        @Path("breedName") breedName: String,
        @Path("subBreedName") subBreedName: String,
    ): Response<AppResponse<String>>
}