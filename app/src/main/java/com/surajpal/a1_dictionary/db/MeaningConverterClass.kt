package com.surajpal.a1_dictionary.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.surajpal.a1_dictionary.model.Meaning

object MeaningListConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun fromMeaningsList(value: List<Meaning>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun toMeaningsList(value: String): List<Meaning> {
        val listType = object : TypeToken<List<Meaning>>() {}.type
        return gson.fromJson(value, listType)
    }
}