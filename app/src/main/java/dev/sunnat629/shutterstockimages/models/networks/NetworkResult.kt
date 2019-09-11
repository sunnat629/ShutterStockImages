package dev.sunnat629.shutterstockimages.models.networks


/**
 * This class will use in the suspend functions of the repository
 * */
sealed class NetworkResult<out T : Any> {

    /**
     * This class will call after fetching the data from server successfully.
     * It contains the data.
     * @param T is the container of the data from the server.
     * */
    data class Success<out T : Any>(val data: T) : NetworkResult<T>()

    /**
     * This class will call if there is any error during fetching the data from server.
     * It contains the error message.
     * @param exception is the message why the fetch failed
     * */
    data class Error(val exception: String) : NetworkResult<Nothing>()

    /**
     * This class will call if the application exceeds its limit.
     * It contains the error message.
     * @param message mentions that the application exceeds its limit.
     * */
    data class RateLimit(val message: String) : NetworkResult<Nothing>()

    /**
     * This class will call if the application exceeds its limit.
     * It contains the error message.
     * @param exception mentions that the application exceeds its limit.
     * */
    data class NoInternet(val exception: String) : NetworkResult<Nothing>()
}