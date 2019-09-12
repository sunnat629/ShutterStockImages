package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.api.repositories.UnAuthRepository
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import javax.inject.Singleton

/**
 * RepositoryModule.kt
 * This is a module class which provides all the Repositories during inject.
 *
 * It includes another module -
 * @see ServiceModule for more details
 * */
@Module(
    includes = [
        ServiceModule::class
    ]
)
class RepositoryModule {

    /**
     * This singleton provider provides {@linkplain ImageRepository repository} which
     * contains all the suspending functions for fetching image related api endpoints.
     *
     * @param imageApiServices is the {@linkplain ImageApiServices service}
     * */
    @Singleton
    @Provides
    fun provideImageRepository(imageApiServices: ImageApiServices): ImageRepository {
        return ImageRepository(imageApiServices)
    }

    /**
     * This singleton provider provides {@code UnAuthRepository repository} which
     * contains all the suspending functions for fetching unauthorized api endpoints.
     *
     * @param unAuthApiServices is the {@linkplain UnAuthApiServices service}
     * */
    @Singleton
    @Provides
    fun provideUnAuthRepository(unAuthApiServices: UnAuthApiServices): UnAuthRepository {
        return UnAuthRepository(unAuthApiServices)
    }
}