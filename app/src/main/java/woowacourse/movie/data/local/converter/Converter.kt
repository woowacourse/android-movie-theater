package woowacourse.movie.data.local.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converter {
    @TypeConverter
    fun toLocalDateTime(value: Long?): LocalDateTime? =
        value?.let { Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime() }

    @TypeConverter
    fun toTimestamp(dateTime: LocalDateTime?): Long? = dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
}
