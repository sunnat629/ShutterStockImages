package dev.sunnat629.shutterstockimages.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.DSConstants.BASE_URL
import dev.sunnat629.shutterstockimages.models.networks.BasicAuthInterceptor
import dev.sunnat629.shutterstockimages.models.networks.RetrofitFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideAuthRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return RetrofitFactory.createRetrofit(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideMakeRetrofitService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }
}