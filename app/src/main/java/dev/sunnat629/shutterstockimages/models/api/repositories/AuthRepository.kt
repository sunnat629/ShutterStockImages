package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.AuthApiServices
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

open class AuthRepository @Inject constructor(private val authApiServices: AuthApiServices): BaseRepository() {

    suspend fun getTest(): NetworkResult<String> {
        return safeApiCall(call = {authApiServices.testAPI()})
    }
}