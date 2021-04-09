package com.cuvva.library_dogapi.domain.repo

import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.remote.base.Result

internal interface BreedsRepo {
    suspend fun getBreeds(): Result<List<Breed>>
    suspend fun getSubBreedImageUrl(breedName: String, subBreedName: String): Result<String>
}