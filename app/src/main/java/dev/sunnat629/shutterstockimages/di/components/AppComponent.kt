package dev.sunnat629.shutterstockimages.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dev.sunnat629.shutterstockimages.di.modules.NetworkModule
import dev.sunnat629.shutterstockimages.di.modules.RepositoryModule
import dev.sunnat629.shutterstockimages.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class
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
}