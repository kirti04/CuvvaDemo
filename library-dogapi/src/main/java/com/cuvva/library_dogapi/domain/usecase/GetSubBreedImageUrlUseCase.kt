package com.cuvva.library_dogapi.domain.usecase

import com.cuvva.library_dogapi.domain.repo.BreedsRepo
import com.cuvva.library_dogapi.remote.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSubBreedImageUrlUseCase @Inject internal constructor(
    private val breedsRepo: BreedsRepo
) {

    suspend fun invoke(breedName: String, subBreedName: String): Result<String> =
        withContext(Dispatchers.IO) {
            breedsRepo.getSubBreedImageUrl(breedName, subBreedName)
        }
}