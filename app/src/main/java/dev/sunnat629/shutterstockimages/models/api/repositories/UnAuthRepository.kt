package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

/**
 * UnAuthRepository.kt
 * This repository class contains all the suspending functions regarding unauthorized endpoint responses.
 * This is just for a demo purpose.
 *
 * @param unAuthApiServices is an injected {@linkplain UnAuthApiServices service}
 * */
class UnAuthRepository @Inject constructor(private val unAuthApiServices: UnAuthApiServices) :
    BaseRepository() {

    /**
     * This suspending function returns a OK message if it works.
     * This is just for a demo purpose.
     * */
    suspend fun getTest(): NetworkResult<String> {
        return safeApiCall(call = { unAuthApiServices.testAPI() })
    }
}