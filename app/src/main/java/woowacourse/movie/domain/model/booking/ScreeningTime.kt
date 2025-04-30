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
        if (selectedDate.isEqual(now.toLocalDate())) {
            return timeOnSelectedDate.filter { it.isAfter(now.toLocalTime()) }
        }
        return timeOnSelectedDate
    }
}
