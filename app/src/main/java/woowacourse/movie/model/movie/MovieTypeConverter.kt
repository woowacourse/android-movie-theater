package woowacourse.movie.model.movie

import androidx.room.TypeConverter
import com.google.gson.Gson

class StringListConverter {
    @TypeConverter
    fun fromJsonToList(value: String?): List<String> {
        return Gson().fromJson(value, Array<String>::class.java).toList()
    }

    @TypeConverter
    fun fromListToJson(value: List<String>?): String {
        return Gson().toJson(value, Array<String>::class.java)
    }
}

class LongListConverter {
    @TypeConverter
    fun fromJsonToList(value: String?): List<Long> {
        return Gson().fromJson(value, Array<Long>::class.java).toList()
    }

    @TypeConverter
    fun fromListToJson(value: List<Long>?): String {
        return Gson().toJson(value, Array<Long>::class.java)
    }
}
