package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.ImageAuthApiServices
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

class ImageRepository @Inject constructor(private val imageApiServices: ImageAuthApiServices): BaseRepository() {

    suspend fun getImages(page: Int): NetworkResult<ImageSearch> {
        return safeApiCall(call = {imageApiServices.getImages(page)})
    }
}