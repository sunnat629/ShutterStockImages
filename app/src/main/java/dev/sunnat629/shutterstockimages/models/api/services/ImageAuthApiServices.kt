package dev.sunnat629.shutterstockimages.models.api.services

import dev.sunnat629.shutterstockimages.models.entities.ImagesSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ImageAuthApiServices {

    @GET("/v2/images/search")
    @Headers("Content-Type: application/json")
    suspend fun getImages(): Response<ImagesSearch>
}