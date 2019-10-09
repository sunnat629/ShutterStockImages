package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.di.qualifiers.Authorized
import dev.sunnat629.shutterstockimages.di.qualifiers.UnAuthorized
import dev.sunnat629.shutterstockimages.models.networks.BasicAuthInterceptor
import dev.sunnat629.shutterstockimages.models.networks.RetrofitFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import javax.inject.Singleton

/**
 * NetworkModule.kt
 * This is the code module and it contains the network related providers.
 * */
@Module
class NetworkModule {

    /**
     * This singleton provider provides an OkHttpClient Builder
     * */
    @Provides
    @Singleton
    fun provideClientBuilder(): OkHttpClient.Builder {
        return RetrofitFactory.createClientBuilder()
    }

    /**
     * This singleton provider provides a {@linkplain BasicAuthInterceptor interceptor}
     * */
    @Provides
    @Singleton
    fun provideBasicAuthInterceptor(): BasicAuthInterceptor {
        return BasicAuthInterceptor()
    }

    /**
     * This singleton provider provides an authorized OkHttpClient
     *
     * @param clientBuilder is an {@code @Authorized} OkHttpClient Builder
     * @param basicAuthInterceptor is a {@linkplain BasicAuthInterceptor interceptor}
     * */
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

    /**
     * This singleton provider provides an authorized Retrofit
     *
     * @param okHttpClient is an {@code @Authorized} OkHttpClient
     * */
    @Provides
    @Singleton
    @Authorized
    fun provideAuthRetrofit(@Authorized okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient, RxJava2CallAdapterFactory.create())
    }

    /**
     * This singleton provider provides an unauthorized OkHttpClient
     *
     * @param clientBuilder is an {@code @UnAuthorized} OkHttpClient Builder
     * */
    @Provides
    @Singleton
    @UnAuthorized
    fun provideOkHttpClient(
        clientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return clientBuilder
            .build()
    }

    /**
     * This singleton provider provides an unauthorized Retrofit
     *
     * @param okHttpClient is an {@code @UnAuthorized} OkHttpClient
     * */
    @Provides
    @Singleton
    @UnAuthorized
    fun provideRetrofit(@UnAuthorized okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient, RxJava2CallAdapterFactory.create())
    }
}