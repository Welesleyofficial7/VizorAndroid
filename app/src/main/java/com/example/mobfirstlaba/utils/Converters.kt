package com.example.mobfirstlaba.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson<List<String>>(it, listType)
        }
    }

    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return Gson().toJson(list)
    }
}

