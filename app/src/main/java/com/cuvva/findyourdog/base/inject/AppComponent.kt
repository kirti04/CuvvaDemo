package com.cuvva.findyourdog.base.inject

import android.app.Application
import com.cuvva.findyourdog.base.CuvvaApplication
import com.cuvva.library_dogapi.inject.LibraryDogApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        LibraryDogApiModule::class]
)

interface AppComponent : AndroidInjector<CuvvaApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    override fun inject(application: CuvvaApplication)
}