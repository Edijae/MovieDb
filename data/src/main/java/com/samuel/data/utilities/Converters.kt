package com.samuel.data.utilities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    val gson = Gson()
    val type = object : TypeToken<ArrayList<Int>>() {}.type
    val stringType = object : TypeToken<ArrayList<String>>() {}.type

    @TypeConverter
    fun listToString(value: List<Int>): String {
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToList(value: String): List<Int> {
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun stringListToString(value: List<String>): String {
        return gson.toJson(value, stringType)
    }

    @TypeConverter
    fun stringToStringList(value: String): List<String> {
        return gson.fromJson(value, stringType)
    }
}