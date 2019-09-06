package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

open class ImageRepository @Inject constructor(private val imageApiServices: ImageApiServices): BaseRepository() {

    suspend fun getImages(): NetworkResult<String> {
        return safeApiCall(call = {imageApiServices.getImages()})
    }
}