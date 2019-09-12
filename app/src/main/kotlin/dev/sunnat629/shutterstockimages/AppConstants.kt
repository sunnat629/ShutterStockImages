package dev.sunnat629.shutterstockimages

/**
 * DSConstants.kt
 * This object class contains all the constant values of this project.
 * */
object DSConstants {
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