package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ScreeningTimeItems(
    val screeningTimes: List<LocalTime> = emptyList(),
) {
    fun getAvailableScreeningTimes(
        nowDateTime: LocalDateTime,
        selectedDate: LocalDate,
    ): List<LocalTime> {
        return if (isToday(nowDateTime, selectedDate)) {
            screeningTimes.filter { it.isAfter(nowDateTime.toLocalTime()) }
        } else {
            screeningTimes
        }
    }

    private fun isToday(
        nowDateTime: LocalDateTime,
        selectedDate: LocalDate,
    ): Boolean = selectedDate.isEqual(nowDateTime.toLocalDate())
}
