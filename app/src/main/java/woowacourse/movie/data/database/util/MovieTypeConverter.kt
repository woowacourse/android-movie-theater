package woowacourse.movie.data.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJsonToList(value: String?): List<String> {
        val arrayType = object : TypeToken<ArrayList<String>>() {}.type
        return gson.fromJson(value, arrayType)
    }

    @TypeConverter
    fun fromListToJson(value: List<String>?): String {
        return gson.toJson(value)
    }
}

class LongListConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromJsonToList(value: String?): List<Long> {
        val arrayType = object : TypeToken<ArrayList<Long>>() {}.type
        return gson.fromJson(value, arrayType)
    }


    @TypeConverter
    fun fromListToJson(value: List<Long>?): String {
        return gson.toJson(value)
    }
}
