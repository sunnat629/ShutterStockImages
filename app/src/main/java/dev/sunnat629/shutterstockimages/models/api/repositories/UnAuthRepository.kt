package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.UnAuthApiServices
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

open class UnAuthRepository @Inject constructor(private val unAuthApiServices: UnAuthApiServices): BaseRepository() {

    suspend fun getTest(): NetworkResult<String> {
        return safeApiCall(call = {unAuthApiServices.testAPI()})
    }
}