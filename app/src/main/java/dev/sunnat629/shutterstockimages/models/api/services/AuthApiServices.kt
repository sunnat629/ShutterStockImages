package dev.sunnat629.shutterstockimages.models.api.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthApiServices {

    @GET("/v2/images/search")
    suspend fun testAPI(): Response<String>
}