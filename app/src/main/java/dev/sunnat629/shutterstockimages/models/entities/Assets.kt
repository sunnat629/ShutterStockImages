package dev.sunnat629.shutterstockimages.models.entities

/**
 * Assets.kt
 * This class contains the images.
 *
 * @param preview has the default size of the image,
 * @param smallThumb has the smaller size of the image,
 * @param largeThumb has the large size of the image,
 * @param hugeThumb has the huge or biggest size of the image.
 * */
data class Assets (
    private val preview: Thumbs,
    private val smallThumb: Thumbs,
    private val largeThumb: Thumbs,
    private val hugeThumb: Thumbs
)