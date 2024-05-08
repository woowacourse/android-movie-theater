package woowacourse.movie.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverters {
    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE
    private val timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    private val localDateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? {
        return value?.format(localDateTimeFormatter)
    }

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it, localDateTimeFormatter) }
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.format(dateFormatter)
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it, dateFormatter) }
    }

    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? {
        return value?.format(timeFormatter)
    }

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it, timeFormatter) }
    }
}
