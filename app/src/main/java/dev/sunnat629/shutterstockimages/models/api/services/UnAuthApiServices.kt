package dev.sunnat629.shutterstockimages.models.api.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UnAuthApiServices {

    @GET("/v2/test")
    @Headers("Content-Type: application/json")
    suspend fun testAPI(): Response<String>
}