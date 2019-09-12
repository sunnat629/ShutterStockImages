package dev.sunnat629.shutterstockimages.models.networks

import android.util.Log
import dev.sunnat629.shutterstockimages.BuildConfig
import dev.sunnat629.shutterstockimages.BuildConfig.BASE_URL
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitFactory.kt
 * This object contains the OkHttpClient.Builder and Retrofit initialization
 * */
object RetrofitFactory {

    /**
     * This function returns an OkHttpClient.Builder
     * */
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

    /**
     * This function returns a Retrofit
     * */
    fun createRetrofit(
        okHttpClient: OkHttpClient,
        callAdapterFactory: CallAdapter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
    }

    /**
     * This function returns a HttpLoggingInterceptor for seeing the log regarding OkHttp in debug mode.
     * */
    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val debugInterceptor = HttpLoggingInterceptor()
        debugInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return debugInterceptor
    }
}
