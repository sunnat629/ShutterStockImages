package dev.sunnat629.shutterstockimages.models.api.repositories

import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import javax.inject.Inject

/**
 * ImageRepository.kt
 * This repository class contains all the suspending functions regarding image related endpoints responses.
 *
 * @param imageApiServices is an injected {@linkplain ImageApiServices service}
 * */
class ImageRepository @Inject constructor(private val imageApiServices: ImageApiServices) :
    BaseRepository() {

    /**
     * This suspending function returns the json which contains the image data
     *
     * @param page is the number of the page number of the fetched data.
     * */
    suspend fun getImages(page: Int): NetworkResult<ImageSearch> {
        return safeApiCall(call = { imageApiServices.getImages(page) })
    }
}