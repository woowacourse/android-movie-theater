package woowacourse.movie.domain.model.movie

import java.io.Serializable
import java.time.LocalDate
import java.time.temporal.ChronoUnit

// 영화관의 상영일
// e.g. 상영일 : 2025.5.1~2025.5.30
data class ScreeningPeriod(
    val startDate: LocalDate,
    val endDate: LocalDate,
) : Serializable {
    operator fun contains(date: LocalDate): Boolean = date in startDate..endDate

    fun bookingDates(today: LocalDate): List<LocalDate> {
        val start = getStartDate(today)
        val days = ChronoUnit.DAYS.between(start, endDate).toInt()
        return (0..days).map { start.plusDays(it.toLong()) }
    }

    private fun getStartDate(other: LocalDate): LocalDate {
        if (startDate.isAfter(other)) {
            return startDate
        }
        return other
    }
}
