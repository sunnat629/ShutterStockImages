package dev.sunnat629.shutterstockimages

/**
 * DSConstants.kt
 * This object class contains all the constant values of this project.
 * */
object DSConstants {
    const val BASE_URL = "https://api.shutterstock.com"
    const val CONSUMER_KEY = "d7ed1-7c154-d3833-aa576-abf10-4ccb2"
    const val CONSUMER_SECRET = "1628d-6afa1-ccb0c-20c4e-16185-463fb"
    const val RATE_LIMIT_CODE = 429
    const val HEADER_AUTHORIZATION = "Authorization"
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 30
    const val INITIAL_LOAD_SIZE = PAGE_SIZE * 2
}

/**
 * LoggingTags.kt
 * This object class contains all the constant values of the logger tag.
 * */
object LoggingTags {
    const val REPOSITORY = "REPOSITORY"
    const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    const val BASE_REPOSITORY = "BASE_REPOSITORY"
    const val DATA_S_FACTORY = "DATA_S_FACTORY"
    const val VIEW_MODEL = "VIEW_MODEL"
}