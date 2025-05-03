package woowacourse.movie.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieScheduler(
    private val screening: Screening,
) {
    fun getBookableDates(today: LocalDate = LocalDate.now()): List<LocalDate> {
        val movie = screening.movie
        val firstDate = if (movie.startDate.isBefore(today)) today else movie.startDate
        return buildList {
            var currentDate = firstDate
            while (!currentDate.isAfter(movie.endDate)) {
                add(currentDate)
                currentDate = currentDate.plusDays(DATE_INTERVAL)
            }
        }
    }

    fun getBookableTimes(
        selectedDate: LocalDate,
        now: LocalDateTime = LocalDateTime.now(),
    ): List<LocalTime> {
        val isToday = selectedDate.isEqual(now.toLocalDate())
        return if (isToday) {
            screening.times.filter { it.isAfter(now.toLocalTime()) }
        } else {
            screening.times
        }
    }

    companion object {
        private const val DATE_INTERVAL = 1L
    }
}
