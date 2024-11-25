package com.example.mobfirstlaba.utils

import android.content.Context
import android.content.SharedPreferences

class AppSettings(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    companion object {
        private const val EMAIL_KEY = "email"
        private const val FONT_SIZE_KEY = "font_size"
        private const val LANGUAGE_KEY = "language_key"
    }

    var email: String?
        get() = sharedPreferences.getString(EMAIL_KEY, null)
        set(value) {
            sharedPreferences.edit().putString(EMAIL_KEY, value).apply()
        }

    var fontSize: Float
        get() = sharedPreferences.getFloat(FONT_SIZE_KEY, 14f)
        set(value) {
            sharedPreferences.edit().putFloat(FONT_SIZE_KEY, value).apply()
        }

    var language: String?
        get() = sharedPreferences.getString(LANGUAGE_KEY, "ru")
        set(value) {
            sharedPreferences.edit().putString(LANGUAGE_KEY, value).apply()
        }
}
