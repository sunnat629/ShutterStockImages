package dev.sunnat629.shutterstockimages.di.components

import dev.sunnat629.shutterstockimages.ui.MainActivity
import dev.sunnat629.shutterstockimages.ui.MainViewModel

/**
 * AppComponent.kt
 * This interface will contains all the inject functions are for activities and viewModels
 * */
interface AppComponent {

    // inject functions are for activities and viewModels

    fun inject(activity: MainActivity)

    fun inject(viewModel: MainViewModel)
}