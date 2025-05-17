package woowacourse.movie.data

import androidx.room.TypeConverter
import woowacourse.movie.util.Formatter.dateHyphenFormatter
import woowacourse.movie.util.Formatter.timeFormatter
import java.time.LocalDate
import java.time.LocalTime

class MovieConverter {
    @TypeConverter
    fun fromDate(date: LocalDate): String = date.format(dateHyphenFormatter)

    @TypeConverter
    fun toDate(date: String): LocalDate = LocalDate.parse(date, dateHyphenFormatter)

    @TypeConverter
    fun fromTime(time: LocalTime): String = time.format(timeFormatter)

    @TypeConverter
    fun toTime(time: String): LocalTime = LocalTime.parse(time, timeFormatter)
}
