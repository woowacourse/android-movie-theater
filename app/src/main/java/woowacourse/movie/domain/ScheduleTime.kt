
package woowacourse.movie.domain

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class ScheduleTime(
    val times: List<LocalTime>,
) : Serializable {
    fun afterCurrentTimeSchedule(currentTime: LocalTime): List<LocalTime> {
        return times.filter { it > currentTime }
    }

    fun afterCurrentDateSchedule(
        date: LocalDateTime,
        currentDateTime: LocalDateTime,
    ): List<LocalTime> {
        if (LocalDate.of(date.year, date.month, date.dayOfMonth) == currentDateTime.toLocalDate()) {
            return afterCurrentTimeSchedule(currentDateTime.toLocalTime())
        }
        return times
    }
}
