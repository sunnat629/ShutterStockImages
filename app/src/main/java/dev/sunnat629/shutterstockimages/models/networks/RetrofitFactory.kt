package dev.sunnat629.shutterstockimages.models.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dev.sunnat629.shutterstockimages.BuildConfig
import dev.sunnat629.shutterstockimages.DSConstants.BASE_URL
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun createClientBuilder(): OkHttpClient.Builder {
        val clientBuilder = OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(makeLoggingInterceptor())
        }
        return clientBuilder
    }

    fun createRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val debugInterceptor = HttpLoggingInterceptor()
        debugInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return debugInterceptor
    }
}
