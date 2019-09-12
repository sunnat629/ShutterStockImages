package dev.sunnat629.shutterstockimages.models.api.services

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * UnAuthApiServices.kt
 * This interface contains all the unauthorized api endpoints.
 * This is just for a demo purpose.
 * */
interface UnAuthApiServices {

    /**
     * This function returns the response a OK message.
     * This is just for a demo purpose.
     * */
    @GET("/v2/test")
    @Headers("Content-Type: application/json")
    fun testAPI(): Single<String>
}