package dev.sunnat629.shutterstockimages.models.entities

import com.google.gson.annotations.SerializedName

/**
 * ImageSearch.kt * This class contains all the data when user fetch the data for the 'search' endpoint,
 *
 * @param page is the page number of the given json data,
 * @param perPage contains a number of the total images of a specific page,
 * @param totalCount contains the number of total images in this 'search' endpoint,
 * @param searchId is an ID that was provided in the results of an image search,
 * @param imageContent is the list of all the image data,
 * @see ImageContent for more details regarding the image data.
 * */
data class ImageSearch(
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("search_id")
    val searchId: String,
    @SerializedName("data")
    val imageContent: List<ImageContent>
)