package woowacourse.movie.database.typeconverter

import androidx.room.TypeConverter
import java.time.LocalDate

class DateConverter {
    @TypeConverter
    fun toLocalDate(value: Long?): LocalDate? = value?.let { LocalDate.ofEpochDay(it) }

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): Long? = date?.toEpochDay()
}
