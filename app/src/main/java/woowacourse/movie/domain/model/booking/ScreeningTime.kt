package woowacourse.movie.domain.model.booking

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScreeningTime(
    private val now: LocalDateTime,
    private val timeOnSelectedDate: List<LocalTime>,
) : Serializable {
    fun getAvailableScreeningTimes(selectedDate: LocalDate): List<LocalTime> {
        val isToday = selectedDate.isEqual(now.toLocalDate())

        if (isToday) {
            return timeOnSelectedDate.filter { it.isAfter(now.toLocalTime()) }
        }
        return timeOnSelectedDate
    }
}
