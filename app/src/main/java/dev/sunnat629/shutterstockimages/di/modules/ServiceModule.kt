package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.di.scopes.Authorized
import dev.sunnat629.shutterstockimages.di.scopes.UnAuthorized
import dev.sunnat629.shutterstockimages.models.api.services.ImageAuthApiServices
import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class]
)
class ServiceModule {

    @Singleton
    @Provides
    fun provideAuthApiServices(@UnAuthorized retrofit: Retrofit): UnAuthApiServices {
        return retrofit.create(UnAuthApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideImageApiServices(@Authorized retrofit: Retrofit): ImageAuthApiServices {
        return retrofit.create(ImageAuthApiServices::class.java)
    }
}