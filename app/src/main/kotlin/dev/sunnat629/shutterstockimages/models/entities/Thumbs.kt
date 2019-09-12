package dev.sunnat629.shutterstockimages.models.entities

/**
 * Thumbs.kt
 * This class contains the size and raw image url.
 *
 * @param height is the height of this image,
 * @param url is the raw URL of this image,
 * @param width is the width of this image.
 * */
data class Thumbs(
    val height: Int,
    val url: String,
    val width: Int
)