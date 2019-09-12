package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.DSConstants.RATE_LIMIT_CODE
import dev.sunnat629.shutterstockimages.LoggingTags.BASE_REPOSITORY
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

/**
 * BaseRepository.kt
 * This class will be extended by all other repository classes.
 * */
open class BaseRepository {

    /**
     * @see safeApiCall is a top-level suspending function for the sake of adding the try/catch to every
     * Network request. It triggers the network requests.
     *
     * @param call is a suspending Response.
     * */
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkResult<T> {

        try {

            // If the application exceeds its limit, it will return NetworkResult.RateLimit with a message
            if (call.invoke().code() == RATE_LIMIT_CODE) {
                Timber.tag(BASE_REPOSITORY).e("Your application exceeds its limit")
                return NetworkResult.RateLimit("Your application exceeds its limit")
            }

            return if (call.invoke().isSuccessful) {
                Timber.tag(BASE_REPOSITORY).d("Data fetched Successfully.")
                NetworkResult.Success(call.invoke().body()!!)
            } else {
                Timber.tag(BASE_REPOSITORY).e("${call.invoke().code()}: ${call.invoke().message()}")
                NetworkResult.Error(call.invoke().message())
            }
        } catch (ioException: IOException) {
            Timber.tag(BASE_REPOSITORY).e("ioException: ${ioException.message}")

            // If the internet connection is no available, it will return NetworkResult.NoInternet with the ioException message
            return NetworkResult.NoInternet("${ioException.message}")
        }
    }
}