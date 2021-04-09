package com.cuvva.findyourdog.base.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cuvva.findyourdog.feature.common.ViewModelFactory
import com.cuvva.findyourdog.feature.detail.BreedDetailActivity
import com.cuvva.findyourdog.feature.detail.BreedDetailViewModel
import com.cuvva.findyourdog.feature.list.BreedListActivity
import com.cuvva.findyourdog.feature.list.BreedListViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun contributeBreedListActivity(): BreedListActivity

    @ContributesAndroidInjector
    abstract fun contributeBreedDetailActivity(): BreedDetailActivity

    @Binds
    @IntoMap
    @ViewModelKey(BreedListViewModel::class)
    abstract fun bindBreedListViewModel(viewModel: BreedListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BreedDetailViewModel::class)
    abstract fun bindBreedDetailViewModel(viewModel: BreedDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}