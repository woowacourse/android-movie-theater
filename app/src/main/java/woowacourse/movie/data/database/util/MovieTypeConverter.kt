package woowacourse.movie.data.database.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringListConverter {
    @TypeConverter
    fun fromJsonToList(value: String?): List<String> {
        val arrayType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(value, arrayType)
    }

    @TypeConverter
    fun fromListToJson(value: List<String>?): String {
        return Gson().toJson(value)
    }
}

class LongListConverter {
    @TypeConverter
    fun fromJsonToList(value: String?): List<Long> {
        val arrayType = object : TypeToken<ArrayList<Long>>() {}.type
        return Gson().fromJson(value, arrayType)
    }

    @TypeConverter
    fun fromListToJson(value: List<Long>?): String {
        return Gson().toJson(value)
    }
}
