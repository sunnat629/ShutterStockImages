package dev.sunnat629.shutterstockimages.models.entities

/**
 * Assets.kt
 * This class contains the images.
 *
 * @param preview has the default size of the image,
 * @param small_thumb has the smaller size of the image,
 * @param large_thumb has the large size of the image,
 * @param huge_thumb has the huge or biggest size of the image.
 * */
data class Assets (
    private val preview: Thumbs,
    private val small_thumb: Thumbs,
    private val large_thumb: Thumbs,
    private val huge_thumb: Thumbs
)