package dev.sunnat629.shutterstockimages.models.entities

/**
 * ImageContent.kt
 * This class contains the data information of an image.
 *
 * @param id contains the id number of the image,
 * @param aspect is the ratio of an image describes the proportional relationship between its width and height,
 * @param assets contains the image in various sizes,
 * @see Assets for more details of assets,
 * @param contributor is the data of the owner or contributor of this image,
 * @see Contributor for more details of contributor,
 * @param description is a summery or caption of this image,
 * @param image_type contains the format or type of this Image; Like - Vector, PNG etc.,
 * @param media_type contains the type of this data; Like - Image, Video, Audio etc.,
 * @param url is the URL link of this image in the ShutterStock site.
 * */
data class ImageContent(
    private val id: String,
    private val aspect: Double,
    private val assets: Assets,
    private val contributor: Contributor,
    private val description: String,
    private val image_type: String,
    private val media_type: String,
    private val url: String
)