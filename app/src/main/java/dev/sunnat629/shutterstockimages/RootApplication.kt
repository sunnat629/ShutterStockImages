package dev.sunnat629.shutterstockimages

import android.app.Application
import dev.sunnat629.shutterstockimages.di.components.AppComponent
import dev.sunnat629.shutterstockimages.di.components.DaggerAppComponent
import timber.log.Timber

/**
 * RootApplication.kt
 * This class uses to specialize tasks that need to run before the creation of your first activity.
 *
 * @property component contains the component interface of the dagger2
 * @property Timber is the logger for this project which is initialized only in Debug Mode.
 * */
class RootApplication : Application() {

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder().application(this).build()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // todo initiate the Firebase or Fabric crashlytics.
        }
    }

    companion object {
        fun getComponent(application: Application): AppComponent {
            return (application.applicationContext as RootApplication).component
        }
    }
}