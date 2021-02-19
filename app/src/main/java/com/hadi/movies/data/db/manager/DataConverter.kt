package com.hadi.movies.data.db.manager

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {
    @TypeConverter
    fun fromPromotionItems(value: List<String>): String {
        return Gson().toJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun toPromotionItems(value: String): List<String> {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }
}