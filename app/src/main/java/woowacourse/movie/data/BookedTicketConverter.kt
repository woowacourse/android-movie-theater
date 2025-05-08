package woowacourse.movie.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BookedTicketConverter {
    private val dateTimeFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    private val gson = Gson()

    @TypeConverter
    fun LocalDateTime.toEntityValue(): String {
        return this.format(dateTimeFormat)
    }

    @TypeConverter
    fun String.toLocalDateTime(): LocalDateTime {
        return LocalDateTime.parse(this, dateTimeFormat)
    }

    @TypeConverter
    fun Set<Seat>.toEntityValue(): String {
        return gson.toJson(this)
    }

    @TypeConverter
    fun String.toSet(): Set<Seat> {
        val type = object : TypeToken<Set<Seat>>() {}.type
        return gson.fromJson(this, type)
    }
}
