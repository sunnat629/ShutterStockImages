package dev.sunnat629.shutterstockimages.di

import javax.inject.Qualifier

/**
 * UnAuthorized.kt
 * This is an annotation class for the unauthorized retrofit and OkHttpClient
 * */
@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class UnAuthorized