package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScreeningTimes(
    private val now: LocalDateTime,
    private val timesOnSelectedDate: List<LocalTime>,
) : Serializable {
    fun bookableTimes(selectedDate: LocalDate): List<LocalTime> {
        val isToday = selectedDate.isEqual(now.toLocalDate())
        return if (isToday) {
            timesOnSelectedDate.filter { time -> time.isAfter(now.toLocalTime()) }
        } else {
            timesOnSelectedDate
        }
    }
}
