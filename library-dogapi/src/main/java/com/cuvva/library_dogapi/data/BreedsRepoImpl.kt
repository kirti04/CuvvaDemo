package com.cuvva.library_dogapi.data

import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.repo.BreedsRepo
import com.cuvva.library_dogapi.remote.base.Result
import javax.inject.Inject

internal class BreedsRepoImpl @Inject constructor(
    private val breedsDataSource: BreedsDataSource
) : BreedsRepo {
    override suspend fun getBreeds(): Result<List<Breed>> = breedsDataSource.getBreeds()

    override suspend fun getSubBreedImageUrl(breedName: String, subBreedName: String): Result<String> =
        breedsDataSource.getSubBreedImageUrl(breedName, subBreedName)
}