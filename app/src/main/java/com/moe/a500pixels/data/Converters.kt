package com.moe.a500pixels.data

import androidx.room.TypeConverter
import java.util.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moe.a500pixels.popular.data.User


/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }

    @TypeConverter
    fun fromUser(user: User?): String? {
        if (user == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.toJson(user, type)
    }

    @TypeConverter
    fun toUser(optionValueString: String?): User? {
        if (optionValueString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.fromJson<User>(optionValueString, type)
    }

    @TypeConverter
    fun fromString(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {

        }.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        return  Gson().toJson(list)
    }
}