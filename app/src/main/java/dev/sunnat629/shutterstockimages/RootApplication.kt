package dev.sunnat629.shutterstockimages

import android.app.Application
import timber.log.Timber

class RootApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        } else {
            // todo initiate the Firebase or Fabric crashlytics.
        }
    }
}