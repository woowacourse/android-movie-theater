package woowacourse.movie.model

import java.time.LocalDate
import java.time.LocalTime

class Scheduler(
    private val movie: Movie,
    private val times: List<LocalTime>,
    private val today: LocalDate = LocalDate.now(),
    private val currentTime: LocalTime = LocalTime.now(),
) {
    fun screeningPeriods(): List<LocalDate> {
        val startDate =
            if (movie.screeningStartDate.isBefore(today)) today else movie.screeningStartDate
        return generateSequence(startDate) { currentDate ->
            val next = currentDate.plusDays(1)
            if (next.isAfter(movie.screeningEndDate)) null else next
        }.toList()
    }

    fun screeningTimes(selectedDate: LocalDate): List<LocalTime> {
        if (today == selectedDate) return times.filter { time -> time.isAfter(currentTime) }
        return times
    }
}
