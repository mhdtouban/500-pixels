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
    fun fromOptionValue(optionValues: User?): String? {
        if (optionValues == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.getType()
        return gson.toJson(optionValues, type)
    }

    @TypeConverter
    fun toOptionValue(optionValuesString: String?): User? {
        if (optionValuesString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.getType()
        return gson.fromJson<User>(optionValuesString, type)
    }
}