package com.cuvva.library_dogapi.domain.usecase

import com.cuvva.library_dogapi.domain.model.Breed
import com.cuvva.library_dogapi.domain.repo.BreedsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.cuvva.library_dogapi.remote.base.Result
import javax.inject.Inject

class GetAllBreedsUseCase @Inject internal constructor(
    private val breedsRepo: BreedsRepo
) {

    suspend fun invoke(): Result<List<Breed>> = withContext(Dispatchers.IO) {
        breedsRepo.getBreeds()
    }
}