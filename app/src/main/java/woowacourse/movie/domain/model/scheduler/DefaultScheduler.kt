package woowacourse.movie.domain.model.scheduler

import woowacourse.movie.domain.model.Screening
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class DefaultScheduler(
    private val screening: Screening,
) : Scheduler {
    override fun getBookableDates(today: LocalDate): List<LocalDate> {
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

    override fun getBookableTimes(
        selectedDate: LocalDate,
        now: LocalDateTime,
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
