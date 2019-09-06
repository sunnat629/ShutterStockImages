package dev.sunnat629.shutterstockimages

import android.app.Application
import dev.sunnat629.shutterstockimages.di.components.AppComponent
import dev.sunnat629.shutterstockimages.di.components.DaggerAppComponent
import timber.log.Timber

class RootApplication: Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().application(this).build()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        } else {
            // todo initiate the Firebase or Fabric crashlytics.
        }
    }

    companion object {
        fun getComponent(application: Application): AppComponent{
            return (application.applicationContext as RootApplication).component
        }
    }
}