package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.services.AuthApiServices
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class]
)
class ServiceModule {

    @Singleton
    @Provides
    fun provideAuthApiServices(retrofit: Retrofit): AuthApiServices {
        return retrofit.create(AuthApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideImageApiServices(retrofit: Retrofit): ImageApiServices {
        return retrofit.create(ImageApiServices::class.java)
    }
}