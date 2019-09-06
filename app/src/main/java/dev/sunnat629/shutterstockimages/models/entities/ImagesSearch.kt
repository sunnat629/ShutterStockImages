package dev.sunnat629.shutterstockimages.models.entities

/**
 * ImagesSearch.kt
 * This class contains all the data when user fetch the data for the 'search' endpoint,
 *
 * @param page is the page number of the given json data,
 * @param per_page contains a number of the total images of a specific page,
 * @param total_count contains the number of total images in this 'search' endpoint,
 * @param search_id is an ID that was provided in the results of an image search,
 * @param data is the list of all the image data,
 * @see ImageContent for more details regarding the image data.
 * */
data class ImagesSearch(
    private val page: Int,
    private val per_page: Int,
    private val total_count: Int,
    private val search_id: String,
    private val data: List<ImageContent>
)