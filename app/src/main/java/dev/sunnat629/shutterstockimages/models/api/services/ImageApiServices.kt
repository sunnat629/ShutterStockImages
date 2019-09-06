package dev.sunnat629.shutterstockimages.models.api.services

import retrofit2.Response
import retrofit2.http.GET

interface ImageApiServices {

    @GET("/v2/images/search")
    suspend fun getImages(): Response<String>
}