package com.cuvva.library_dogapi.remote.source

import com.cuvva.library_dogapi.data.BreedsDataSource
import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.extension.abortOnError
import com.cuvva.library_dogapi.extension.mapSuccess
import com.cuvva.library_dogapi.remote.base.BaseRemoteSource
import com.cuvva.library_dogapi.remote.base.Result
import com.cuvva.library_dogapi.remote.base.RetrofitServiceFactory
import java.util.*
import javax.inject.Inject

internal class BreedsDataSourceImpl @Inject constructor(
    private val serviceFactory: RetrofitServiceFactory
) : BaseRemoteSource(), BreedsDataSource {

    private val service = serviceFactory.make(BreedsService::class.java)

    override suspend fun getBreeds(): Result<List<Breed>> {
        return safeApiCall {
            serviceFactory.make(BreedsService::class.java).getBreeds()
        }.mapSuccess {
            val breeds = it.value.message.toList()
            breeds.map { breed ->
                val subBreedsResponse =
                    safeApiCall { service.getSubBreeds(breed) }.abortOnError {
                        Result.Success(
                            emptyList<Breed>()
                        )
                    }
                val subBreeds = subBreedsResponse.message.toList().map { name ->
                    Breed.SubBreed(name = name)
                }

                val breedImageResponse =
                    safeApiCall { service.getBreedImage(breed) }.abortOnError {
                        Result.Success(
                            emptyList<Breed>()
                        )
                    }
                val breedImage = breedImageResponse.message

                Breed(name = breed, image = breedImage, subBreeds = subBreeds)
            }
        }
    }

    override suspend fun getSubBreedImageUrl(
        breedName: String,
        subBreedName: String
    ): Result<String> {
        return safeApiCall {
            service.getSubBreedImage(breedName, subBreedName)
        }.mapSuccess { it.value.message }
    }
}