package dev.sunnat629.shutterstockimages.di.components

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dev.sunnat629.shutterstockimages.di.modules.NetworkModule
import dev.sunnat629.shutterstockimages.di.modules.OthersModule
import dev.sunnat629.shutterstockimages.di.modules.ServiceModule
import dev.sunnat629.shutterstockimages.di.modules.ViewModelModule
import javax.inject.Singleton

/**
 * AppComponent.kt
 * The singleton component interface is responsible for providing application instances.
 *
 * It includes some modules -
 * @see NetworkModule for more details
 * @see OthersModule for more details

 * */
@Singleton
@Component(
    modules = [
        NetworkModule::class,
        ServiceModule::class,
        OthersModule::class,
        ViewModelModule::class
    ]
)
interface AppComponentDefault : AppComponent {

    /**
     * Here, it binds some instance to Component. In this case I created an interface with
     * {@linkplain @Component.Builder annotation} and add whatever function I want to add to builder.
     * In this case, Application is add to the AppComponent.
     * */
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponentDefault
    }
}