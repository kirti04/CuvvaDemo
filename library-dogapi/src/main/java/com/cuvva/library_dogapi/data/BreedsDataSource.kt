package com.cuvva.library_dogapi.data

import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.remote.base.Result

internal interface BreedsDataSource {
    suspend fun getBreeds(): Result<List<Breed>>
    suspend fun getSubBreedImageUrl(breedName: String, subBreedName: String): Result<String>
}