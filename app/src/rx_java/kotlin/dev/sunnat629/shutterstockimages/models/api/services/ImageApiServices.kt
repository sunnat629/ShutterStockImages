package dev.sunnat629.shutterstockimages.models.api.services

import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * ImageApiServices.kt
 * This interface contains all the image related api endpoints.
 * */
interface ImageApiServices {

    /**
     * This function returns the Single Observable of the {@code ImageSearch}
     * */
    @GET("/v2/images/search")
    @Headers("Content-Type: application/json")
    fun getImages(@Query("page") page: Int): Observable<ImageSearch>
}