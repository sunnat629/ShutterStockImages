package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.di.Authorized
import dev.sunnat629.shutterstockimages.di.UnAuthorized
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * ServiceModule.kt
 * This is a module class which provides all the Services during inject.
 *
 * It includes another module -
 * @see NetworkModule for more details
 * */
@Module(
    includes = [
        NetworkModule::class]
)
class ServiceModule {

    /**
     * This singleton provider provides an unauthorized service which contains all unauthorized the
     * api endpoints.
     *
     * @param retrofit is an {@code @UnAuthorized} Retrofit
     * */
    @Singleton
    @Provides
    fun provideUnAuthApiServices(@UnAuthorized retrofit: Retrofit): UnAuthApiServices {
        return retrofit.create(UnAuthApiServices::class.java)
    }

    /**
     * This singleton provider provides an authorized image related service which
     * contains all authorized the api endpoints regarding images.
     *
     * @param retrofit is an {@code @Authorized} Retrofit
     * */
    @Singleton
    @Provides
    fun provideImageApiServices(@Authorized retrofit: Retrofit): ImageApiServices {
        return retrofit.create(ImageApiServices::class.java)
    }
}