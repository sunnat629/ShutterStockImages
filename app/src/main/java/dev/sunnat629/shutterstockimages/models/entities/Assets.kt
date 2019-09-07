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
    val preview: Thumbs,
    val small_thumb: Thumbs,
    val large_thumb: Thumbs,
    val huge_thumb: Thumbs
)