package woowacourse.movie.data.db.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateTimeConverter {
  @TypeConverter
  fun fromLocalDateTime(dateTime: LocalDateTime?): Long? {
    return dateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
  }

  @TypeConverter
  fun toLocalDateTime(millis: Long?): LocalDateTime? {
    return millis?.let {
      Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
  }
}
