package woowacourse.movie.domain.model.booking

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Schedule(
    val items: List<LocalDateTime>,
) {
    val startDate: LocalDate get() = items.first().toLocalDate()
    val endDate: LocalDate get() = items.last().toLocalDate()

    fun bookableDates(now: LocalDateTime): List<LocalDate> {
        return items.map { schedule -> schedule.toLocalDate() }
            .filter { date -> !date.isBefore(earliestDate(now)) }
            .distinct()
    }

    fun bookableTimes(
        selectedDate: LocalDate,
        now: LocalDateTime,
    ): List<LocalTime> {
        val times =
            items.filter { schedule -> schedule.toLocalDate() == selectedDate }
                .map { schedule -> schedule.toLocalTime() }
                .distinct()
        return if (selectedDate == now.toLocalDate()) {
            times.filter { time -> time.isAfter(now.toLocalTime()) }
        } else {
            times
        }
    }

    private fun earliestDate(now: LocalDateTime): LocalDate {
        val today = now.toLocalDate()
        val bookableTimes = bookableTimes(today, now)
        return when {
            startDate.isAfter(today) -> startDate
            bookableTimes.isNotEmpty() -> today
            else -> today.plusDays(1)
        }
    }
}
