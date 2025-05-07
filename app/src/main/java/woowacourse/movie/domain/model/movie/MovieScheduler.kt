package woowacourse.movie.domain.model.movie

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieScheduler(
    private val startDate: LocalDate,
    private val endDate: LocalDate,
) {
    fun getBookableDates(now: LocalDate = LocalDate.now()): List<LocalDate> {
        val bookableDates = mutableListOf<LocalDate>()
        var current = startDate

        while (!current.isAfter(endDate)) {
            if (!current.isBefore(now)) {
                bookableDates.add(current)
            }
            current = current.plusDays(DAYS_TO_ADD)
        }

        return bookableDates
    }

    fun getBookableTimes(
        date: LocalDate,
        now: LocalDateTime = LocalDateTime.now(),
        screeningTimes: List<LocalTime>,
    ): List<LocalTime> {
        val isToday = date.isEqual(now.toLocalDate())
        return if (isToday) {
            screeningTimes.filter {
                it.isAfter(now.toLocalTime())
            }
        } else {
            screeningTimes
        }
    }

    companion object {
        private const val DAYS_TO_ADD = 1L
    }
}
