package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.repositories.AuthRepository
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.api.services.AuthApiServices
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import javax.inject.Singleton

@Module(
    includes = [
        ServiceModule::class
    ]
)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideImageRepository(imageApiServices: ImageApiServices): ImageRepository {
        return ImageRepository(imageApiServices)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(authApiServices: AuthApiServices): AuthRepository {
        return AuthRepository(authApiServices)
    }
}