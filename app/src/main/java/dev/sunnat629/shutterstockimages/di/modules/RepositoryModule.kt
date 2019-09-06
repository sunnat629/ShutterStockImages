package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.repositories.UnAuthRepository
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.api.services.ImageAuthApiServices
import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import javax.inject.Singleton

@Module(
    includes = [
        ServiceModule::class
    ]
)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(imageApiServices: ImageAuthApiServices): ImageRepository {
        return ImageRepository(imageApiServices)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(unAuthApiServices: UnAuthApiServices): UnAuthRepository {
        return UnAuthRepository(unAuthApiServices)
    }
}