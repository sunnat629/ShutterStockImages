package dev.sunnat629.shutterstockimages.models.api.services

import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ImageAuthApiServices {

    @GET("/v2/images/search")
    @Headers("Content-Type: application/json")
    suspend fun getImages(@Query("page") page: Int): Response<ImageSearch>
}