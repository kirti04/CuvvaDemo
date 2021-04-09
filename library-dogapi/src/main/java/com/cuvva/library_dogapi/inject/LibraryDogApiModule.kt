package com.cuvva.library_dogapi.inject

import com.cuvva.library_dogapi.remote.source.BreedsDataSourceImpl
import com.cuvva.library_dogapi.data.BreedsDataSource
import com.cuvva.library_dogapi.data.BreedsRepoImpl
import com.cuvva.library_dogapi.domain.repo.BreedsRepo
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class LibraryDogApiModule {

    @Binds
    internal abstract fun bindBreedsRepo(breedsRepo: BreedsRepoImpl): BreedsRepo

    @Singleton
    @Binds
    internal abstract fun bindsBreedsRemoteDataSource(breedsDataSourceImpl: BreedsDataSourceImpl): BreedsDataSource
}