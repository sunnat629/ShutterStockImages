package dev.sunnat629.shutterstockimages.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dev.sunnat629.shutterstockimages.di.modules.CoRoutinesModule
import dev.sunnat629.shutterstockimages.di.modules.NetworkModule
import dev.sunnat629.shutterstockimages.di.modules.RepositoryModule
import dev.sunnat629.shutterstockimages.ui.MainActivity
import dev.sunnat629.shutterstockimages.ui.MainViewModel
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        CoRoutinesModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(viewModel: MainViewModel)
}