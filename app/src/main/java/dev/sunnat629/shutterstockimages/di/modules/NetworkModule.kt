package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.di.scopes.Authorized
import dev.sunnat629.shutterstockimages.di.scopes.UnAuthorized
import dev.sunnat629.shutterstockimages.models.networks.BasicAuthInterceptor
import dev.sunnat629.shutterstockimages.models.networks.RetrofitFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideClientBuilder(): OkHttpClient.Builder {
        return RetrofitFactory.createUnauthorizedClientBuilder()
    }

    @Provides
    @Singleton
    fun provideBasicAuthInterceptor(): BasicAuthInterceptor {
        return BasicAuthInterceptor()
    }

    @Provides
    @Singleton
    @Authorized
    fun provideAuthOkHttpClient(
        clientBuilder: OkHttpClient.Builder,
        basicAuthInterceptor: BasicAuthInterceptor
    ): OkHttpClient {
        return clientBuilder
            .addInterceptor(basicAuthInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @Authorized
    fun provideAuthRetrofit(@Authorized okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient)
    }

    @Provides
    @Singleton
    @UnAuthorized
    fun provideOkHttpClient(
        clientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return clientBuilder
            .build()
    }

    @Provides
    @Singleton
    @UnAuthorized
    fun provideRetrofit(@UnAuthorized okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient)
    }
}