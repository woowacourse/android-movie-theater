package woowacourse.movie.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converter {
    private val dateHyphenFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d")
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @TypeConverter
    fun localDateToString(localDate: LocalDate): String = localDate.format(dateHyphenFormatter)

    @TypeConverter
    fun stringToLocalDate(string: String): LocalDate = LocalDate.parse(string, dateHyphenFormatter)

    @TypeConverter
    fun localTimeToString(localTime: LocalTime): String = localTime.format(timeFormatter)

    @TypeConverter
    fun stringToLocalTime(string: String): LocalTime = LocalTime.parse(string, timeFormatter)
}
