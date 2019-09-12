package dev.sunnat629.shutterstockimages.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dev.sunnat629.shutterstockimages.di.modules.NetworkModule
import dev.sunnat629.shutterstockimages.di.modules.OthersModule
import dev.sunnat629.shutterstockimages.di.modules.RepositoryModule
import dev.sunnat629.shutterstockimages.ui.MainActivity
import dev.sunnat629.shutterstockimages.ui.MainViewModel
import javax.inject.Singleton

/**
 * AppComponent.kt
 * The singleton component interface is responsible for providing application instances.
 *
 * It includes some modules -
 * @see NetworkModule for more details
 * @see RepositoryModule for more details
 * @see OthersModule for more details

 * */
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        OthersModule::class
    ]
)
interface AppComponent {

    /**
     * Here, it binds some instance to Component. In this case I created an interface with
     * {@linkplain @Component.Builder annotation} and add whatever function I want to add to builder.
     * In this case, Application is add to the AppComponent.
     * */
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    // inject functions are for activities and viewModels
    fun inject(activity: MainActivity)

    fun inject(viewModel: MainViewModel)
}