package dev.sunnat629.shutterstockimages.models.entities

/**
 * ImagesSearch.kt
 * This class contains all the data when user fetch the data for the 'search' endpoint,
 *
 * @param page is the page number of the given json data,
 * @param perPage contains a number of the total images of a specific page,
 * @param totalCount contains the number of total images in this 'search' endpoint,
 * @param searchId is an ID that was provided in the results of an image search,
 * @param data is the list of all the image data,
 * @see ImageContent for more details regarding the image data.
 * */
data class ImagesSearch(
    private val page: Int,
    private val perPage: Int,
    private val totalCount: Int,
    private val searchId: String,
    private val data: List<ImageContent>
)