package dev.sunnat629.shutterstockimages

import android.content.Context
import androidx.multidex.MultiDexApplication
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.sunnat629.shutterstockimages.di.components.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * RootApplication.kt
 * This class uses to specialize tasks that need to run before the creation of your first activity.
 *
 * @property Timber is the logger for this project which is initialized only in Debug Mode.
 * */
class RootApplication : MultiDexApplication(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // todo initiate the Firebase or Fabric crashlytics.
        }
    }

    companion object {

        @get:Synchronized
        lateinit var instance: RootApplication

        fun get(context: Context): RootApplication = context.applicationContext as RootApplication
    }
}