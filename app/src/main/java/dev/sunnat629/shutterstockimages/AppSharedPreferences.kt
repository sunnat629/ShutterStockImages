package dev.sunnat629.shutterstockimages

import android.content.SharedPreferences
import javax.inject.Inject


class AppSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) {

    var username: String
        get() = sharedPreferences.getString(USERNAME, "") ?: ""
        set(value) {
            sharedPreferences.edit().putString(USERNAME, value).apply()
        }

    var password: String
        get() = sharedPreferences.getString(PASSWORD, "") ?: ""
        set(value) {
            sharedPreferences.edit().putString(PASSWORD, value).apply()
        }



    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }
}