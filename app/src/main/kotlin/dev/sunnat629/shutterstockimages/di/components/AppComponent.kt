package dev.sunnat629.shutterstockimages.di.components

import dev.sunnat629.shutterstockimages.ui.MainActivity

/**
 * AppComponent.kt
 * This interface will contains all the inject functions are for activities and viewModels
 * */
interface AppComponent {

    fun inject(activity: MainActivity)
}